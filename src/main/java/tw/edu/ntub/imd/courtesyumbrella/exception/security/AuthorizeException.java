package tw.edu.ntub.imd.courtesyumbrella.exception.security;

import tw.edu.ntub.imd.birc.common.exception.ProjectException;

public class AuthorizeException extends ProjectException {
    public AuthorizeException(Throwable cause) {
        super("您並無權限執行此要求", cause);
    }

    @Override
    public String getErrorCode() {
        return "User - AccessDenied";
    }
}
