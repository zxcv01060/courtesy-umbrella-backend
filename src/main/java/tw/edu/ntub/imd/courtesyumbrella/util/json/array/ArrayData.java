package tw.edu.ntub.imd.courtesyumbrella.util.json.array;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import tw.edu.ntub.imd.birc.common.util.date.DatePatternBuilder;
import tw.edu.ntub.imd.birc.common.util.date.LocalDateTimeUtils;
import tw.edu.ntub.imd.birc.common.util.date.LocalDateUtils;
import tw.edu.ntub.imd.birc.common.util.date.LocalTimeUtils;
import tw.edu.ntub.imd.birc.common.wrapper.date.DateWrapper;
import tw.edu.ntub.imd.courtesyumbrella.util.http.ResponseUtils;
import tw.edu.ntub.imd.courtesyumbrella.util.json.ResponseData;
import tw.edu.ntub.imd.courtesyumbrella.util.json.object.ObjectData;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ArrayData implements ResponseData {
    private final ArrayNode arrayNode;

    public ArrayData() {
        this(ResponseUtils.createMapper()
                     .createArrayNode());
    }

    public ArrayData(ArrayNode arrayNode) {
        this.arrayNode = arrayNode;
    }

    public int length() {
        return size();
    }

    public int size() {
        return arrayNode.size();
    }

    public ArrayData add(int value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Integer value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(long value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Long value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(float value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Float value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(double value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Double value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(boolean value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Boolean value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(BigDecimal value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable BigInteger value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(String value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(byte[] bytes) {
        arrayNode.add(bytes);
        return this;
    }

    public ArrayData add(DateWrapper<?> dateWrapper, DatePatternBuilder builder) {
        arrayNode.add(dateWrapper.format(builder));
        return this;
    }

    public ArrayData add(LocalDate localDate, DatePatternBuilder builder) {
        arrayNode.add(LocalDateUtils.format(localDate, builder));
        return this;
    }

    public ArrayData add(LocalTime localTime, DatePatternBuilder builder) {
        arrayNode.add(LocalTimeUtils.format(localTime, builder));
        return this;
    }

    public ArrayData add(LocalDateTime localDateTime, DatePatternBuilder builder) {
        arrayNode.add(LocalDateTimeUtils.format(localDateTime, builder));
        return this;
    }

    public ArrayData add(ResponseData responseData) {
        return add(responseData.getData());
    }

    public ArrayData add(JsonNode jsonNode) {
        arrayNode.add(jsonNode);
        return this;
    }

    public ObjectData addObject() {
        return new ObjectData(arrayNode.addObject());
    }

    public ArrayData addArray() {
        return new ArrayData(arrayNode.addArray());
    }

    public CollectionArrayData addCollectionToThis() {
        return new CollectionArrayData(this);
    }

    public CollectionArrayData addCollectionToNewArray() {
        return new CollectionArrayData(addArray());
    }

    public MapArrayData addMapToThis() {
        return new MapArrayData(this);
    }

    public MapArrayData addMapToNewArray() {
        return new MapArrayData(addArray());
    }

    public <T> Consumer<T> getAddObjectConsumer(BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return t -> addObjectDataConsumer.accept(addObject(), t);
    }

    public JsonNode get(int index) {
        return arrayNode.get(index);
    }

    @Override
    public ArrayNode getData() {
        return arrayNode;
    }
}
