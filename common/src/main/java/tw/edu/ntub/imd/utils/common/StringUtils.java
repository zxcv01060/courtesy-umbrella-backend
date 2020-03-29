package tw.edu.ntub.imd.utils.common;

public class StringUtils {
    private StringUtils() {

    }

    public static int length(String string) {
        return string != null ? string.length() : 0;
    }

    public static boolean contains(String string, String target) {
        return string != null && string.contains(target);
    }

    public static boolean startWith(String string, String target) {
        return string != null && string.startsWith(target);
    }

    public static boolean endWith(String string, String target) {
        return string != null && string.endsWith(target);
    }

    public static boolean allEquals(String string, String... otherStringArray) {
        for (String otherString : otherStringArray) {
            if (!string.equals(otherString)) {
                return false;
            }
        }
        return true;
    }
}
