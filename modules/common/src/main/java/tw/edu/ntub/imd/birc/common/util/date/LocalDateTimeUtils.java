package tw.edu.ntub.imd.birc.common.util.date;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.DateParseException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalDateTimeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@UtilityClass
@SuppressWarnings("unused")
public class LocalDateTimeUtils {

    @Nonnull
    public LocalDateTimeWrapper createWrapper() {
        return new LocalDateTimeWrapper();
    }

    @Nonnull
    public LocalDateTimeWrapper createWrapper(@Nonnull LocalDateTime localDateTime) throws NullParameterException {
        return new LocalDateTimeWrapper(localDateTime);
    }

    @Nonnull
    public String nowString() {
        return nowString(DatePatternBuilder.DEFAULT_DATE_TIME);
    }

    @Nonnull
    public String nowString(@Nonnull DatePatternBuilder builder) {
        return format(now(), builder);
    }

    @Nonnull
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Nonnull
    public String format(@Nullable LocalDateTime localDateTime, @Nonnull DatePatternBuilder builder) {
        return localDateTime != null ? localDateTime.format(builder.buildFormatter()) : "";
    }

    @Nullable
    public LocalDateTime parseIgnoreException(@Nonnull String dateString) {
        try {
            return parse(dateString);
        } catch (DateParseException e) {
            return null;
        }
    }

    @Nonnull
    public LocalDateTime parse(@Nonnull String dateString) throws DateParseException {
        for (DatePatternBuilder builder : DatePatternBuilder.DEFAULT_BUILDER_ARRAY) {
            try {
                return LocalDateTime.parse(dateString, builder.buildFormatter());
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DateParseException(dateString);
    }

    @Nullable
    public LocalDateTime parseIgnoreException(@Nonnull String dateString, @Nonnull DatePatternBuilder builder) {
        try {
            return parse(dateString, builder);
        } catch (DateParseException e) {
            return null;
        }
    }

    @Nonnull
    public LocalDateTime parse(@Nonnull String dateString, @Nonnull DatePatternBuilder builder) throws DateParseException {
        try {
            return LocalDateTime.parse(dateString, builder.buildFormatter());
        } catch (DateTimeParseException ignored) {
            throw new DateParseException(dateString, builder.build());
        }
    }
}
