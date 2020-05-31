package tw.edu.ntub.imd.courtesyumbrella.util.json.object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import tw.edu.ntub.imd.birc.common.util.date.DatePatternBuilder;
import tw.edu.ntub.imd.birc.common.util.date.LocalDateTimeUtils;
import tw.edu.ntub.imd.birc.common.util.date.LocalDateUtils;
import tw.edu.ntub.imd.birc.common.util.date.LocalTimeUtils;
import tw.edu.ntub.imd.birc.common.wrapper.date.DateWrapper;
import tw.edu.ntub.imd.courtesyumbrella.util.http.ResponseUtils;
import tw.edu.ntub.imd.courtesyumbrella.util.json.ResponseData;
import tw.edu.ntub.imd.courtesyumbrella.util.json.array.ArrayData;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ObjectData implements ResponseData {
    private final ObjectNode objectNode;

    public ObjectData() {
        this(ResponseUtils.createMapper()
                .createObjectNode());
    }

    public ObjectData(String jsonString) {
        this((ObjectNode) ResponseUtils.createJsonNode(jsonString));
    }

    public ObjectData(ObjectNode objectNode) {
        this.objectNode = objectNode;
    }

    public ObjectData add(String key, ObjectData objectData) {
        return add(key, objectData.objectNode);
    }

    public ObjectData add(String key, ArrayData arrayData) {
        return add(key, arrayData.getData());
    }

    public ObjectData add(String key, ResponseData responseData) {
        return add(key, responseData.getData());
    }

    public ObjectData add(String key, JsonNode jsonNode) {
        return replace(key, jsonNode);
    }

    public ObjectData replace(String key, JsonNode jsonNode) {
        objectNode.set(key, jsonNode);
        return this;
    }

    public ObjectData add(String key, short value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Short value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, int value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Integer value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, long value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Long value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, float value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Float value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, double value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Double value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, boolean value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Boolean value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, BigDecimal value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable BigInteger value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, String value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, byte[] bytes) {
        objectNode.put(key, bytes);
        return this;
    }

    public ObjectData add(String key, DateWrapper<?> dateWrapper, DatePatternBuilder builder) {
        objectNode.put(key, dateWrapper.format(builder));
        return this;
    }

    public ObjectData add(String key, LocalDate localDate, DatePatternBuilder builder) {
        objectNode.put(key, LocalDateUtils.format(localDate, builder));
        return this;
    }

    public ObjectData add(String key, LocalTime localTime, DatePatternBuilder builder) {
        objectNode.put(key, LocalTimeUtils.format(localTime, builder));
        return this;
    }

    public ObjectData add(String key, LocalDateTime localDateTime, DatePatternBuilder builder) {
        objectNode.put(key, LocalDateTimeUtils.format(localDateTime, builder));
        return this;
    }

    public ObjectData addObject(String key) {
        return new ObjectData(objectNode.putObject(key));
    }

    public ArrayData addArray(String key) {
        return new ArrayData(objectNode.putArray(key));
    }

    public CollectionObjectData addCollectionToThis() {
        return new CollectionObjectData(this);
    }

    public CollectionObjectData addCollectionToNewObject(String key) {
        return new CollectionObjectData(addObject(key));
    }

    public MapObjectData addMapToThis() {
        return new MapObjectData(this);
    }

    public MapObjectData addMapToNewObject(String key) {
        return new MapObjectData(addObject(key));
    }

    public <T> Consumer<T> getAddObjectConsumer(String key, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return t -> addObjectDataConsumer.accept(addObject(key), t);
    }

    @Override
    public ObjectNode getData() {
        return objectNode;
    }

    public Integer getInt(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asInt();
        } else {
            return null;
        }
    }

    public Long getLong(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asLong();
        } else {
            return null;
        }
    }

    public Double getDouble(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asDouble();
        } else {
            return null;
        }
    }

    public Boolean getBoolean(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asBoolean();
        } else {
            return null;
        }
    }

    public String getString(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asText();
        } else {
            return null;
        }
    }
}
