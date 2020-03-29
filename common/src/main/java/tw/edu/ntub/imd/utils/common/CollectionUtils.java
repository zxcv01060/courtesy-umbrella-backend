package tw.edu.ntub.imd.utils.common;

import javax.annotation.Nullable;
import java.util.Collection;

public final class CollectionUtils {
    private CollectionUtils() {

    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
