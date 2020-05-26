package tw.edu.ntub.imd.courtesyumbrella.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.ResponseEntity;
import tw.edu.ntub.imd.birc.common.dto.CodeEntry;
import tw.edu.ntub.imd.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.birc.common.util.function.TripleConsumer;
import tw.edu.ntub.imd.courtesyumbrella.util.function.AddObjectDataConsumer;
import tw.edu.ntub.imd.courtesyumbrella.util.function.AddObjectDataMapConsumer;
import tw.edu.ntub.imd.courtesyumbrella.util.json.ResponseData;
import tw.edu.ntub.imd.courtesyumbrella.util.json.array.ArrayData;
import tw.edu.ntub.imd.courtesyumbrella.util.json.array.CollectionArrayData;
import tw.edu.ntub.imd.courtesyumbrella.util.json.array.MapArrayData;
import tw.edu.ntub.imd.courtesyumbrella.util.json.object.ObjectData;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 回傳標準格式
 * {
 * "result": Boolean,
 * "errorCode": String,
 * "message": String,
 * "data": Object/Array
 * }
 */
public class ResponseEntityBuilder {
    private final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
    private boolean success;
    private ProjectException ProjectException;
    private String message;
    private ResponseData responseData;

    private ResponseEntityBuilder(ProjectException ProjectException) {
        this(false);
        this.ProjectException = ProjectException;
        this.message = ProjectException.getMessage();
    }

    private ResponseEntityBuilder(boolean success) {
        this.success = success;
    }

    public static ResponseEntityBuilder success() {
        return new ResponseEntityBuilder(true);
    }

    public static ResponseEntityBuilder error() {
        return new ResponseEntityBuilder(false);
    }

    public static ResponseEntityBuilder error(@Nonnull ProjectException ProjectException) {
        ProjectException.printStackTrace();
        return new ResponseEntityBuilder(ProjectException);
    }

    public ResponseEntityBuilder result(boolean isSuccess) {
        success = isSuccess;
        return this;
    }

    public ResponseEntityBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntityBuilder emptyObject() {
        return data(new ObjectData());
    }

    public ResponseEntityBuilder emptyArray() {
        return data(new ArrayData());
    }

    public ResponseEntityBuilder data(Collection<? extends CodeEntry> resource) {
        return data(new CollectionArrayData(resource));
    }

    public <T> ResponseEntityBuilder data(Collection<T> resource, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    public <T> ResponseEntityBuilder data(Collection<T> resource, AddObjectDataConsumer<T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    public ResponseEntityBuilder data(Map<String, String> resource) {
        return data(new MapArrayData(resource));
    }

    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        return data(new MapArrayData(resource, addObjectDataConsumer));
    }

    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, AddObjectDataMapConsumer<K, V> addObjectDataMapConsumer) {
        return data(new MapArrayData(resource, addObjectDataMapConsumer));
    }

    public ResponseEntityBuilder data(ResponseData responseData) {
        this.responseData = responseData;
        return this;
    }

    public ResponseEntityBuilder addHeader(String name, String... valueArray) {
        bodyBuilder.header(name, valueArray);
        return this;
    }

    public ResponseEntity<String> build() {
        return bodyBuilder.body(buildJSONString());
    }

    public String buildJSONString() {
        try {
            ObjectData result = new ObjectData()
                    .add("result", success)
                    .add("errorCode", ProjectException != null ? ProjectException.getErrorCode() : "")
                    .add("message", message)
                    .replace("data",
                             responseData != null ?
                                     responseData.getData() :
                                     new ObjectData().getData()
                    );
            String body = ResponseUtils.createMapper()
                    .writeValueAsString(result.getData());
            System.out.println("Response JSON = " + body);
            return body;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"result\": false, \"errorCode\": \"Server - JsonProcessError\", \"message\": \"" +
                    e.getMessage() +
                    "\", \"data\": " +
                    (responseData.getData() instanceof ArrayNode ? "[]" : "{}") +
                    "}";
        }
    }
}
