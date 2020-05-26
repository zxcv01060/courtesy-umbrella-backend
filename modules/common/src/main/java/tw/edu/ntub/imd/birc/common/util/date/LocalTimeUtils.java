package tw.edu.ntub.imd.birc.common.util.date;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.DateParseException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalTimeWrapper;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@UtilityClass
@SuppressWarnings("unused")
public class LocalTimeUtils {

    public LocalTimeWrapper createWrapper() {
        return new LocalTimeWrapper();
    }

    public LocalTimeWrapper createWrapper(LocalTime localTime) throws NullParameterException {
        return new LocalTimeWrapper(localTime);
    }

    public LocalTime now() {
        return LocalTime.now();
    }

    public String format(LocalTime localTime, DatePatternBuilder builder) {
        return localTime != null ? localTime.format(builder.buildFormatter()) : "";
    }

    public LocalTime parseIgnoreException(String timeString) {
        try {
            return parse(timeString);
        } catch (DateParseException e) {
            return null;
        }
    }

    public LocalTime parse(String timeString) throws DateParseException {
        for (DatePatternBuilder builder : DatePatternBuilder.DEFAULT_TIME_BUILDER_ARRAY) {
            try {
                return LocalTime.parse(timeString, builder.buildFormatter());
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DateParseException(timeString);
    }

    public LocalTime parseIgnoreException(String timeString, DatePatternBuilder builder) {
        try {
            return parse(timeString, builder);
        } catch (DateParseException e) {
            return null;
        }
    }

    public LocalTime parse(String timeString, DatePatternBuilder builder) throws DateParseException {
        try {
            return LocalTime.parse(timeString, builder.buildFormatter());
        } catch (DateTimeParseException ignored) {
            throw new DateParseException(timeString, builder.build());
        }
    }
}
