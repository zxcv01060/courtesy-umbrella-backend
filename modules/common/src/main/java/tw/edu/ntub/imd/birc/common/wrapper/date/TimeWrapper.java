package tw.edu.ntub.imd.birc.common.wrapper.date;

import tw.edu.ntub.imd.birc.common.util.date.DatePatternBuilder;

public interface TimeWrapper<T> {
    int getHour();

    TimeWrapper<T> setHour(int amount);

    TimeWrapper<T> addHour(int amount);

    int getMinute();

    TimeWrapper<T> setMinute(int amount);

    TimeWrapper<T> addMinute(int amount);

    int getSecond();

    TimeWrapper<T> setSecond(int amount);

    TimeWrapper<T> addSecond(int amount);

    int getMillisecond();

    TimeWrapper<T> setMillisecond(int amount);

    TimeWrapper<T> addMillisecond(int amount);

    String format(DatePatternBuilder builder);

    T get();

    boolean isBefore(T t);

    boolean isAfter(T t);

    boolean isEqual(T t);

    default boolean isLargeHour(int hour) {
        return getHour() > hour;
    }

    default boolean isLargeOrEqualHour(int hour) {
        return getHour() >= hour;
    }

    default boolean isBetweenHour(int firstHour, int lastHour) {
        return getHour() >= firstHour && getHour() <= lastHour;
    }

    default boolean isLargeMinute(int minute) {
        return getMinute() > minute;
    }

    default boolean isLargeOrEqualMinute(int minute) {
        return getMinute() >= minute;
    }

    default boolean isBetweenMinute(int firstMinute, int lastMinute) {
        return getMinute() >= firstMinute && getMinute() <= lastMinute;
    }

    default boolean isLargeSecond(int second) {
        return getSecond() > second;
    }

    default boolean isLargeOrEqualSecond(int second) {
        return getSecond() >= second;
    }

    default boolean isBetweenSecond(int firstSecond, int lastSecond) {
        return getSecond() >= firstSecond && getSecond() <= lastSecond;
    }

    default boolean isLargeMillisecond(int millisecond) {
        return getMillisecond() > millisecond;
    }

    default boolean isLargeOrEqualMillisecond(int millisecond) {
        return getMillisecond() >= millisecond;
    }

    default boolean isBetweenMillisecond(int firstMillisecond, int lastMillisecond) {
        return getMillisecond() >= firstMillisecond && getMillisecond() <= lastMillisecond;
    }
}
