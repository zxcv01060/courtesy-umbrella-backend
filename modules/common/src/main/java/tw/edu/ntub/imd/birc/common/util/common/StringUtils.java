package tw.edu.ntub.imd.birc.common.util.common;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
@SuppressWarnings("unused")
public class StringUtils {

    public int length(String string) {
        return string != null ? string.length() : 0;
    }

    public boolean contains(String string, String target) {
        return string != null && string.contains(target);
    }

    public boolean startWith(String string, String target) {
        return string != null && string.startsWith(target);
    }

    public boolean endWith(String string, String target) {
        return string != null && string.endsWith(target);
    }

    public boolean allEquals(String string, String... otherStringArray) {
        for (String otherString : otherStringArray) {
            if (!string.equals(otherString)) {
                return false;
            }
        }
        return true;
    }

    public String getUUID() {
        return UUID.randomUUID()
                .toString();
    }
}
