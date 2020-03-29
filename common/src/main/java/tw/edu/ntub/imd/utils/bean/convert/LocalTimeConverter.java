package tw.edu.ntub.imd.utils.bean.convert;

import tw.edu.ntub.imd.exception.NullParameterException;
import tw.edu.ntub.imd.utils.date.LocalTimeUtils;

import javax.annotation.Nonnull;
import java.time.LocalTime;

public class LocalTimeConverter implements CopyConverter<LocalTime, LocalTimeUtils.LocalTimeWrapper> {
    @Override
    public LocalTimeUtils.LocalTimeWrapper convert(@Nonnull LocalTime localTime) {
        try {
            return new LocalTimeUtils.LocalTimeWrapper(localTime);
        } catch (NullParameterException e) {
            return null;
        }
    }
}
