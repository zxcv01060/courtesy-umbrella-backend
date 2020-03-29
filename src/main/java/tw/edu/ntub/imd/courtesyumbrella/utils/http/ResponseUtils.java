package tw.edu.ntub.imd.courtesyumbrella.utils.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.text.StringEscapeUtils;
import tw.edu.ntub.imd.exception.DateParseException;
import tw.edu.ntub.imd.exception.NullParameterException;
import tw.edu.ntub.imd.exception.UnknownException;
import tw.edu.ntub.imd.utils.date.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ResponseUtils {
    // JSON的Content-Type字串
    public static final String JSON_MIME_TYPE = "application/json; charset=UTF-8";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule("DateModule", new Version(1, 0, 0, null, null, null));
        simpleModule.addDeserializer(Date.class, new JsonDeserializer<>() {
            @Override
            public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return DateUtils.parseIgnoreException(p.getValueAsString());
            }
        });
        simpleModule.addDeserializer(Calendar.class, new JsonDeserializer<>() {
            @Override
            public Calendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return CalendarUtils.parseIgnoreException(p.getValueAsString());
            }
        });
        simpleModule.addDeserializer(LocalDate.class, new JsonDeserializer<>() {
            @Override
            public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return LocalDateUtils.parseIgnoreException(p.getValueAsString());
            }
        });
        simpleModule.addDeserializer(LocalDateUtils.LocalDateWrapper.class, new JsonDeserializer<>() {
            @Override
            public LocalDateUtils.LocalDateWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                try {
                    return new LocalDateUtils.LocalDateWrapper(LocalDateUtils.parse(p.getValueAsString()));
                } catch (DateParseException | NullParameterException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
        simpleModule.addDeserializer(LocalTime.class, new JsonDeserializer<>() {
            @Override
            public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return LocalTimeUtils.parseIgnoreException(p.getValueAsString());
            }
        });
        simpleModule.addDeserializer(LocalTimeUtils.LocalTimeWrapper.class, new JsonDeserializer<>() {
            @Override
            public LocalTimeUtils.LocalTimeWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                try {
                    return new LocalTimeUtils.LocalTimeWrapper(LocalTimeUtils.parse(p.getValueAsString()));
                } catch (DateParseException | NullParameterException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
        simpleModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return LocalDateTimeUtils.parseIgnoreException(p.getValueAsString());
            }
        });
        simpleModule.addDeserializer(LocalDateTimeUtils.LocalDateTimeWrapper.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTimeUtils.LocalDateTimeWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                try {
                    return new LocalDateTimeUtils.LocalDateTimeWrapper(LocalDateTimeUtils.parse(p.getValueAsString()));
                } catch (DateParseException | NullParameterException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
        simpleModule.addDeserializer(Boolean.class, new JsonDeserializer<>() {
            @Override
            public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getValueAsString();
                if (value == null || value.isBlank()) {
                    return null;
                }
                return value.equals("1") || value.equalsIgnoreCase("true");
            }
        });
        simpleModule.addDeserializer(String.class, new JsonDeserializer<>() {
            @Override
            public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                if (p.getCurrentValue() instanceof Boolean) {
                    return p.getValueAsBoolean() ? "1" : "0";
                }
                return p.getValueAsString();
            }
        });
        mapper.registerModule(simpleModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private ResponseUtils() {

    }

    public static JsonNode createJsonNode(String jsonString) {
        try {
            return mapper.readTree(jsonString);
        } catch (IOException e) {
            throw new UnknownException(e);
        }
    }

    public static JsonNode createJsonNode(Map<?, ?> map) {
        return mapper.valueToTree(map);
    }

    public static ObjectMapper createMapper() {
        return mapper;
    }

    public static <D> D readValue(String jsonString, Class<D> jsonStringClass) {
        try {
            String unEscapeString = StringEscapeUtils.unescapeJson(jsonString);
            if (unEscapeString.startsWith("\"")) {
                unEscapeString = unEscapeString.replaceFirst("\"", "");
            }
            if (unEscapeString.endsWith("\"")) {
                unEscapeString = unEscapeString.substring(0, unEscapeString.length() - 1);
            }
            return mapper.readValue(unEscapeString, jsonStringClass);
        } catch (IOException e) {
            throw new UnknownException(e);
        }
    }
}
