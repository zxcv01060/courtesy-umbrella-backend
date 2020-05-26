package tw.edu.ntub.imd.birc.common.util.bean.convert;

import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalDateTimeWrapper;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

public class LocalDateTimeConverter implements CopyConverter<LocalDateTime, LocalDateTimeWrapper> {
    @Override
    public LocalDateTimeWrapper convert(@Nonnull LocalDateTime localDateTime) {
        try {
            return new LocalDateTimeWrapper(localDateTime);
        } catch (NullParameterException e) {
            return null;
        }
    }
}
