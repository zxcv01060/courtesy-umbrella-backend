package tw.edu.ntub.imd.birc.common.wrapper.date;

import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.util.date.DatePatternBuilder;
import tw.edu.ntub.imd.birc.common.util.date.LocalDateUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class LocalDateWrapper implements DateWrapper<LocalDate> {
    private final LocalDate localDate;

    public LocalDateWrapper() {
        this.localDate = LocalDateUtils.now();
    }

    public LocalDateWrapper(LocalDate localDate) throws NullParameterException {
        if (localDate == null) {
            throw new NullParameterException();
        }
        this.localDate = localDate;
    }

    private LocalDateWrapper newInstance(LocalDate localDate) {
        try {
            return new LocalDateWrapper(localDate);
        } catch (NullParameterException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getYear() {
        return localDate.getYear();
    }

    @Override
    public LocalDateWrapper setYear(int amount) {
        return newInstance(localDate.withYear(amount));
    }

    @Override
    public LocalDateWrapper addYear(int amount) {
        return newInstance(localDate.plusYears(amount));
    }

    @Override
    public int getMonth() {
        return localDate.getMonthValue();
    }

    @Override
    public LocalDateWrapper setMonth(int amount) {
        return newInstance(localDate.withMonth(amount));
    }

    @Override
    public LocalDateWrapper addMonth(int amount) {
        return newInstance(localDate.plusMonths(amount));
    }

    @Override
    public int getDay() {
        return localDate.getDayOfMonth();
    }

    @Override
    public LocalDateWrapper setDay(int amount) {
        return newInstance(localDate.withDayOfMonth(amount));
    }

    @Override
    public LocalDateWrapper addDay(int amount) {
        return newInstance(localDate.plusDays(amount));
    }

    @Override
    public long getFullMillisecond() {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }

    @Override
    public String format(DatePatternBuilder builder) {
        return LocalDateUtils.format(localDate, builder);
    }

    @Override
    public LocalDate get() {
        return localDate;
    }

    @Override
    public Date toDate() {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    @Override
    public boolean isBefore(LocalDate localDate) {
        return this.localDate.isBefore(localDate);
    }

    @Override
    public boolean isAfter(LocalDate localDate) {
        return this.localDate.isAfter(localDate);
    }

    @Override
    public boolean isEqual(LocalDate localDate) {
        return this.localDate.isEqual(localDate);
    }
}
