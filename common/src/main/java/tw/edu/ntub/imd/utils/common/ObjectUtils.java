package tw.edu.ntub.imd.utils.common;

public class ObjectUtils {
    private ObjectUtils() {

    }

    public static boolean isNotEquals(Object object, Object target) {
        return !equals(object, target);
    }

    public static boolean equals(Object object, Object target) {
        return object != null && object.equals(target);
    }
}
