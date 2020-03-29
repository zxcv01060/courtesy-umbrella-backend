package tw.edu.ntub.imd.utils.bean.convert;

import tw.edu.ntub.imd.exception.NullParameterException;
import tw.edu.ntub.imd.utils.date.LocalDateUtils;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class LocalDateConverter implements CopyConverter<LocalDate, LocalDateUtils.LocalDateWrapper> {
    @Override
    public LocalDateUtils.LocalDateWrapper convert(@Nonnull LocalDate localDate) {
        try {
            return new LocalDateUtils.LocalDateWrapper(localDate);
        } catch (NullParameterException e) {
            return null;
        }
    }
}
