package tw.edu.ntub.imd.config.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils() {

    }

    public static String getLoginUserAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
