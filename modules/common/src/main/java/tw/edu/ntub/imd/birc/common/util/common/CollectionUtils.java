package tw.edu.ntub.imd.birc.common.util.common;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.Collection;

@UtilityClass
@SuppressWarnings("unused")
public class CollectionUtils {

    public boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
