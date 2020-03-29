package tw.edu.ntub.imd.utils.bean.convert;

import tw.edu.ntub.imd.exception.NullParameterException;
import tw.edu.ntub.imd.utils.date.LocalDateTimeUtils;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

public class LocalDateTimeConverter implements CopyConverter<LocalDateTime, LocalDateTimeUtils.LocalDateTimeWrapper> {
    @Override
    public LocalDateTimeUtils.LocalDateTimeWrapper convert(@Nonnull LocalDateTime localDateTime) {
        try {
            return new LocalDateTimeUtils.LocalDateTimeWrapper(localDateTime);
        } catch (NullParameterException e) {
            return null;
        }
    }
}
