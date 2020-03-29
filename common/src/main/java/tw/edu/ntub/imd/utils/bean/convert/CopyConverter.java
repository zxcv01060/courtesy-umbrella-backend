package tw.edu.ntub.imd.utils.bean.convert;

import javax.annotation.Nonnull;

public interface CopyConverter<S, T> {
    T convert(@Nonnull S s);
}
