package tw.edu.ntub.imd.courtesyumbrella.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.HttpStatus;
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
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * <p>回傳標準格式</p>
 * <pre>
 * {
 *     "result": Boolean,
 *     "errorCode": String,
 *     "message": String,
 *     "data": Object/Array
 * }
 * </pre>
 */
@SuppressWarnings("unused")
public class ResponseEntityBuilder {
    private final ResponseEntity.BodyBuilder bodyBuilder;
    private boolean success;
    private ProjectException ProjectException;
    private String message;
    private ResponseData responseData;

    private ResponseEntityBuilder(@Nonnull ResponseEntity.BodyBuilder bodyBuilder, @Nonnull ProjectException ProjectException) {
        this(bodyBuilder, false);
        this.ProjectException = ProjectException;
        this.message = ProjectException.getMessage();
    }

    private ResponseEntityBuilder(boolean success) {
        this(ResponseEntity.ok(), success);
    }

    private ResponseEntityBuilder(@Nonnull ResponseEntity.BodyBuilder bodyBuilder, boolean success) {
        this.bodyBuilder = bodyBuilder;
        this.success = success;
    }

    /**
     * <p>靜態構造方法，創建一個status = 200且回傳body如下的ResponseEntityBuilder</p>
     *
     * <pre>
     * {
     *     "result": true
     * }
     * </pre>
     *
     * @return result = {@literal true}且status = 200的ResponseEntityBuilder
     */
    public static ResponseEntityBuilder success() {
        return new ResponseEntityBuilder(true);
    }

    /**
     * <p>靜態構造方法，創建一個status = 200且回傳body如下的ResponseEntityBuilder</p>
     *
     * <pre>
     * {
     *     "result": false
     * }
     * </pre>
     *
     * @return result = {@literal false}且status = {@literal 200}的ResponseEntityBuilder
     */
    public static ResponseEntityBuilder error() {
        return new ResponseEntityBuilder(false);
    }

    /**
     * <p>靜態構造方法，創建一個status = {@literal 200}且回傳body如下的ResponseEntityBuilder</p>
     *
     * <pre>
     * {
     *     "result": false,
     *     "errorCode": projectException.getErrorCode(),
     *     "message": projectException.getMessage()
     * }
     * </pre>
     *
     * @param projectException 錯誤原因，由此提供errorCode與message，不可為{@code null}
     * @return result = {@literal false}且status = {@literal 200}的ResponseEntityBuilder
     * @see ProjectException
     * @see ProjectException#getErrorCode()
     * @see Throwable#getMessage()
     */
    public static ResponseEntityBuilder error(@Nonnull ProjectException projectException) {
        return error(HttpStatus.OK, projectException);
    }

    /**
     * <p>靜態構造方法，創建一個status = {@code status}且回傳body如下的ResponseEntityBuilder</p>
     *
     * <pre>
     * {
     *     "result": false,
     *     "errorCode": projectException.getErrorCode(),
     *     "message": projectException.getMessage()
     * }
     * </pre>
     *
     * @param status           Http狀態碼，不可為{@code null}
     * @param projectException 錯誤原因，由此提供errorCode與message，不可為{@code null}
     * @return result = {@literal false}且status = {@code status}的ResponseEntityBuilder
     * @see HttpStatus
     * @see ProjectException
     * @see ProjectException#getErrorCode()
     * @see Throwable#getMessage()
     */
    public static ResponseEntityBuilder error(@Nonnull HttpStatus status, @Nonnull ProjectException projectException) {
        projectException.printStackTrace();
        return new ResponseEntityBuilder(ResponseEntity.status(status), projectException);
    }

    /**
     * 設定回傳資料的result值
     *
     * @param isSuccess 是否成功
     * @return ResponseEntityBuilder本身
     */
    public ResponseEntityBuilder result(boolean isSuccess) {
        success = isSuccess;
        return this;
    }

    /**
     * 設定回傳資料的message值
     *
     * @param message 訊息，可以是{@code null}
     * @return ResponseEntityBuilder本身
     */
    public ResponseEntityBuilder message(@Nullable String message) {
        this.message = message;
        return this;
    }

    /**
     * 設定回傳資料的data為空的物件(Object)
     *
     * @return ResponseEntityBuilder本身
     * @apiNote 請務必與原本方法的回傳data型態一致，若不一致會導致App端出現錯誤
     */
    public ResponseEntityBuilder emptyObject() {
        return data(new ObjectData());
    }

    /**
     * 設定回傳資料的data為空的陣列(Array)
     *
     * @return ResponseEntityBuilder本身
     * @apiNote 請務必與原本方法的回傳data型態一致，若不一致會導致App端出現錯誤
     */
    public ResponseEntityBuilder emptyArray() {
        return data(new ArrayData());
    }

    /**
     * 設定回傳資料的data值為JsonEntry陣列
     *
     * <pre>
     * {
     *     "data": [
     *         {
     *             "name": codeEntry.getName(),
     *             "value": codeEntry.getValue()
     *         }
     *     ]
     * }
     * </pre>
     *
     * @param resource JsonEntry的Collection，不可為{@code null}
     * @return ResponseEntityBuilder本身
     */
    public ResponseEntityBuilder data(@Nonnull Collection<? extends CodeEntry> resource) {
        return data(new CollectionArrayData(resource));
    }

    /**
     * 用來快速將集合類型的資料轉換成陣列型式並設定到data中
     *
     * @param resource              數據來源
     * @param addObjectDataConsumer {@code FunctionalInterface}，轉換{@code resource}的每一個元素為ObjectData，不可為{@code null}，其中會傳入全新的{@code ObjectData}與集合內的單一元素
     * @param <T>                   {@code resource}的泛型
     * @return ResponseEntityBuilder本身
     * @see Collection
     * @see java.util.Set
     * @see java.util.List
     * @see FunctionalInterface
     * @see BiConsumer
     * @see CollectionArrayData#add(Collection, BiConsumer)
     * @see ObjectData
     */
    public <T> ResponseEntityBuilder data(@Nonnull Collection<T> resource, @Nonnull BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    /**
     * 用來快速將集合類型的資料轉換成陣列型式並設定到data中
     *
     * @param resource              數據來源
     * @param addObjectDataConsumer {@code FunctionalInterface}，轉換{@code resource}的每一個元素為ObjectData，不可為{@code null}，其中會傳入全新的{@code ObjectData}、當前迭代的索引值、集合內的單一元素
     * @param <T>                   {@code resource}的泛型
     * @return ResponseEntityBuilder本身
     * @see Collection
     * @see java.util.Set
     * @see java.util.List
     * @see FunctionalInterface
     * @see AddObjectDataConsumer
     * @see CollectionArrayData#add(Collection, AddObjectDataConsumer)
     * @see ObjectData
     */
    public <T> ResponseEntityBuilder data(Collection<T> resource, AddObjectDataConsumer<T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    /**
     * 用來快速將{@code Map}類型的資料轉換成陣列型式並設定到data中，每一對{@code Map.Entry}轉換結果如下
     *
     * <pre>
     * {
     *     "name": Map.Entry.getKey(),
     *     "value": Map.Entry.getValue()
     * }
     * </pre>
     *
     * @param resource 數據來源
     * @return ResponseEntityBuilder本身
     * @see Map
     * @see Map.Entry
     * @see Map#forEach(BiConsumer)
     * @see FunctionalInterface
     * @see MapArrayData#add(Map)
     */
    public ResponseEntityBuilder data(Map<String, String> resource) {
        return data(new MapArrayData(resource));
    }

    /**
     * 用來快速將Map類型的資料轉換成陣列形式並設定到data中
     *
     * @param resource              數據來源
     * @param addObjectDataConsumer {@code FunctionalInterface}，轉換{@code resource}的每一個{@code Map.Entry}為{@code ObjectData}，不可為{@code null}，其中會傳入全新的{@code ObjectData}、{@code Map.Entry.getKey()}、{@code Map.Entry.getValue()}
     * @param <K>                   Map的Key型態
     * @param <V>                   Map的Value型態
     * @return ResponseEntityBuilder本身
     * @see Map
     * @see Map.Entry
     * @see TripleConsumer
     * @see MapArrayData#add(Map, TripleConsumer)
     * @see ObjectData
     */
    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        return data(new MapArrayData(resource, addObjectDataConsumer));
    }

    /**
     * 用來快速將Map類型的資料轉換成陣列形式並設定到data中
     *
     * @param resource                 數據來源
     * @param addObjectDataMapConsumer {@code FunctionalInterface}，轉換{@code resource}的每一個{@code Map.Entry}為{@code ObjectData}，不可為{@code null}，其中會傳入全新的{@code ObjectData}、{@code index}、{@code Map.Entry.getKey()}、{@code Map.Entry.getValue()}
     * @param <K>                      Map的Key型態
     * @param <V>                      Map的Value型態
     * @return ResponseEntityBuilder本身
     * @see Map
     * @see Map.Entry
     * @see AddObjectDataMapConsumer
     * @see MapArrayData#add(Map, AddObjectDataMapConsumer)
     * @see ObjectData
     */
    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, AddObjectDataMapConsumer<K, V> addObjectDataMapConsumer) {
        return data(new MapArrayData(resource, addObjectDataMapConsumer));
    }

    /**
     * 設定標準回傳格式的data
     *
     * @param responseData FunctionalInterface，回傳data的值
     * @return ResponseEntityBuilder本身
     * @see FunctionalInterface
     * @see ResponseData
     * @see ObjectData
     * @see ArrayData
     * @see tw.edu.ntub.imd.courtesyumbrella.util.json.object.CollectionObjectData
     * @see tw.edu.ntub.imd.courtesyumbrella.util.json.object.MapObjectData
     * @see CollectionArrayData
     * @see MapArrayData
     */
    public ResponseEntityBuilder data(ResponseData responseData) {
        this.responseData = responseData;
        return this;
    }

    /**
     * 設定Response的標頭(Header)，可多次呼叫
     *
     * @param name       Header名稱
     * @param valueArray Header的值
     * @return ResponseEntityBuilder本身
     * @see ResponseEntity.BodyBuilder#header(String, String...)
     */
    public ResponseEntityBuilder addHeader(String name, String... valueArray) {
        bodyBuilder.header(name, valueArray);
        return this;
    }

    /**
     * 建立實際的ResponseEntity
     *
     * @return 設定完成的ResponseEntity
     * @see ResponseEntity
     */
    public ResponseEntity<String> build() {
        return bodyBuilder.body(buildJSONString());
    }

    /**
     * 建立標準回傳格式的JSON字串
     *
     * @return 標準回傳格式的JSON字串
     */
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
