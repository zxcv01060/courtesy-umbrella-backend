package tw.edu.ntub.imd.birc.common.util.common;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public class ObjectUtils {

    public boolean isNotEquals(Object object, Object target) {
        return !equals(object, target);
    }

    public boolean equals(Object object, Object target) {
        return object != null && object.equals(target);
    }
}
