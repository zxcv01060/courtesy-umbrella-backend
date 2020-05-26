package tw.edu.ntub.imd.birc.common.wrapper.date;

import tw.edu.ntub.imd.birc.common.util.date.DatePatternBuilder;

import java.util.Calendar;
import java.util.Date;

public interface DateWrapper<D> {
    int getYear();

    DateWrapper<D> setYear(int amount);

    DateWrapper<D> addYear(int amount);

    int getMonth();

    DateWrapper<D> setMonth(int amount);

    DateWrapper<D> addMonth(int amount);

    int getDay();

    DateWrapper<D> setDay(int amount);

    DateWrapper<D> addDay(int amount);

    long getFullMillisecond();

    String format(DatePatternBuilder builder);

    D get();

    Date toDate();

    boolean isBefore(D d);

    boolean isAfter(D d);

    boolean isEqual(D d);

    default Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate());
        return calendar;
    }

    default boolean isLargeYear(int year) {
        return getYear() > year;
    }

    default boolean isLargeOrEqualYear(int year) {
        return getYear() >= year;
    }

    default boolean isBetweenYear(int firstYear, int lastYear) {
        return getYear() >= firstYear && getYear() <= lastYear;
    }

    default boolean isLargeMonth(int month) {
        return getMonth() > month;
    }

    default boolean isLargeOrEqualMonth(int month) {
        return getMonth() >= month;
    }

    default boolean isBetweenMonth(int firstMonth, int lastMonth) {
        return getMonth() >= firstMonth && getMonth() <= lastMonth;
    }

    default boolean isLargeDay(int day) {
        return getDay() > day;
    }

    default boolean isLargeOrEqualDay(int day) {
        return getDay() >= day;
    }

    default boolean isBetweenDay(int firstDay, int lastDay) {
        return getDay() >= firstDay && getDay() <= lastDay;
    }
}
