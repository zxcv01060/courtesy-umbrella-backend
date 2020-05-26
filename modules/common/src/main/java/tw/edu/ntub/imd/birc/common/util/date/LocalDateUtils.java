package tw.edu.ntub.imd.birc.common.util.date;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.DateParseException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalDateWrapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@UtilityClass
@SuppressWarnings("unused")
public class LocalDateUtils {

    public LocalDateWrapper createWrapper() {
        return new LocalDateWrapper();
    }

    public LocalDateWrapper createWrapper(LocalDate localDate) throws NullParameterException {
        return new LocalDateWrapper(localDate);
    }

    public LocalDate now() {
        return LocalDate.now();
    }

    public String format(LocalDate localDate, DatePatternBuilder builder) {
        return localDate != null ? localDate.format(builder.buildFormatter()) : "";
    }

    public LocalDate parseIgnoreException(String dateString) {
        try {
            return parse(dateString);
        } catch (DateParseException e) {
            return null;
        }
    }

    public LocalDate parse(String dateString) throws DateParseException {
        for (DatePatternBuilder builder : DatePatternBuilder.DEFAULT_BUILDER_ARRAY) {
            try {
                return LocalDate.parse(dateString, builder.buildFormatter());
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DateParseException(dateString);
    }

    public LocalDate parseIgnoreException(String dateString, DatePatternBuilder builder) {
        try {
            return parse(dateString, builder);
        } catch (DateParseException e) {
            return null;
        }
    }

    public LocalDate parse(String dateString, DatePatternBuilder builder) throws DateParseException {
        try {
            return LocalDate.parse(dateString, builder.buildFormatter());
        } catch (DateTimeParseException ignored) {
            throw new DateParseException(dateString, builder.build());
        }
    }


}
