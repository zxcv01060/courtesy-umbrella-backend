package tw.edu.ntub.imd.birc.common.util.date;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.DateParseException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalDateTimeWrapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@UtilityClass
@SuppressWarnings("unused")
public class LocalDateTimeUtils {

    public LocalDateTimeWrapper createWrapper() {
        return new LocalDateTimeWrapper();
    }

    public LocalDateTimeWrapper createWrapper(LocalDateTime localDateTime) throws NullParameterException {
        return new LocalDateTimeWrapper(localDateTime);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public String format(LocalDateTime localDateTime, DatePatternBuilder builder) {
        return localDateTime != null ? localDateTime.format(builder.buildFormatter()) : "";
    }

    public LocalDateTime parseIgnoreException(String dateString) {
        try {
            return parse(dateString);
        } catch (DateParseException e) {
            return null;
        }
    }

    public LocalDateTime parse(String dateString) throws DateParseException {
        for (DatePatternBuilder builder : DatePatternBuilder.DEFAULT_BUILDER_ARRAY) {
            try {
                return LocalDateTime.parse(dateString, builder.buildFormatter());
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DateParseException(dateString);
    }

    public LocalDateTime parseIgnoreException(String dateString, DatePatternBuilder builder) {
        try {
            return parse(dateString, builder);
        } catch (DateParseException e) {
            return null;
        }
    }

    public LocalDateTime parse(String dateString, DatePatternBuilder builder) throws DateParseException {
        try {
            return LocalDateTime.parse(dateString, builder.buildFormatter());
        } catch (DateTimeParseException ignored) {
            throw new DateParseException(dateString, builder.build());
        }
    }
}
