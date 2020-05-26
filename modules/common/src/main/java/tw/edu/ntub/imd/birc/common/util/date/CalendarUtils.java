package tw.edu.ntub.imd.birc.common.util.date;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.DateParseException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalDateTimeWrapper;

import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class CalendarUtils {

    public LocalDateTimeWrapper createWrapper() {
        return new LocalDateTimeWrapper();
    }

    public LocalDateTimeWrapper createWrapper(Calendar calendar) throws NullParameterException {
        return new LocalDateTimeWrapper(calendar);
    }

    public Calendar now() {
        return Calendar.getInstance();
    }

    public Calendar getCalendar(long millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        return calendar;
    }

    public Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public String format(Calendar calendar, DatePatternBuilder builder) {
        return calendar != null ? DateUtils.format(calendar.getTime(), builder) : "";
    }

    public Calendar parseIgnoreException(String dateString) {
        try {
            return parse(dateString);
        } catch (DateParseException e) {
            return null;
        }
    }

    public Calendar parse(String dateString) throws DateParseException {
        for (DatePatternBuilder builder : DatePatternBuilder.DEFAULT_BUILDER_ARRAY) {
            try {
                return parse(dateString, builder);
            } catch (DateParseException ignore) {

            }
        }
        throw new DateParseException(dateString);
    }

    public Calendar parseIgnoreException(String dateString, DatePatternBuilder builder) {
        try {
            return parse(dateString, builder);
        } catch (DateParseException ignore) {
            return null;
        }
    }

    public Calendar parse(String dateString, DatePatternBuilder builder) throws DateParseException {
        Calendar calendar = now();
        calendar.setTime(DateUtils.parse(dateString, builder));
        return calendar;
    }
}
