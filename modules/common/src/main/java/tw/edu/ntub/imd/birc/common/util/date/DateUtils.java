package tw.edu.ntub.imd.birc.common.util.date;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.FastDateFormat;
import tw.edu.ntub.imd.birc.common.exception.DateParseException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalDateTimeWrapper;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

@UtilityClass
@SuppressWarnings("unused")
public class DateUtils {

    public LocalDateTimeWrapper createWrapper() {
        return new LocalDateTimeWrapper();
    }

    public LocalDateTimeWrapper createWrapper(Date date) throws NullParameterException {
        return new LocalDateTimeWrapper(date);
    }

    public boolean isCurrentYear(int year) {
        LocalDate localDate = LocalDateUtils.now();
        return localDate.getYear() == year;
    }

    public Date now() {
        return CalendarUtils.now()
                .getTime();
    }

    public String format(Date date, DatePatternBuilder builder) {
        FastDateFormat dateFormat = FastDateFormat.getInstance(builder.build());
        return date != null ? dateFormat.format(date) : "";
    }

    public Date parseIgnoreException(String dateString) {
        try {
            return parse(dateString);
        } catch (DateParseException e) {
            return null;
        }
    }

    public Date parse(String dateString) throws DateParseException {
        return parse(dateString, DatePatternBuilder.DEFAULT_PATTERN_ARRAY);
    }

    private Date parse(String dateString, String... patterArray) throws DateParseException {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateString, patterArray);
        } catch (ParseException ignore) {
            throw new DateParseException(dateString);
        }
    }

    public Date parseIgnoreException(String dateString, DatePatternBuilder builder) {
        try {
            return parse(dateString, builder);
        } catch (DateParseException ignore) {
            return null;
        }
    }

    public Date parse(String dateString, DatePatternBuilder builder) throws DateParseException {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateString, builder.build());
        } catch (ParseException e) {
            throw new DateParseException(dateString, builder.build());
        }
    }
}
