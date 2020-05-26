package tw.edu.ntub.imd.birc.common.util.bean.convert;

import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalTimeWrapper;

import javax.annotation.Nonnull;
import java.time.LocalTime;

public class LocalTimeConverter implements CopyConverter<LocalTime, LocalTimeWrapper> {
    @Override
    public LocalTimeWrapper convert(@Nonnull LocalTime localTime) {
        try {
            return new LocalTimeWrapper(localTime);
        } catch (NullParameterException e) {
            return null;
        }
    }
}
