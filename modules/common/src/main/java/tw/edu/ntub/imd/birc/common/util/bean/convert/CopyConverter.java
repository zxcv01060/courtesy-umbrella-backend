package tw.edu.ntub.imd.birc.common.util.bean.convert;

import javax.annotation.Nonnull;

public interface CopyConverter<S, T> {
    T convert(@Nonnull S s);
}
