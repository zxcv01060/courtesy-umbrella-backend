package tw.edu.ntub.imd.birc.common.util.bean.convert;

import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.wrapper.date.LocalDateWrapper;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class LocalDateConverter implements CopyConverter<LocalDate, LocalDateWrapper> {
    @Override
    public LocalDateWrapper convert(@Nonnull LocalDate localDate) {
        try {
            return new LocalDateWrapper(localDate);
        } catch (NullParameterException e) {
            return null;
        }
    }
}
