package tw.edu.ntub.imd.birc.common.exception;

public class UnknownException extends ProjectException {
    public UnknownException(Throwable cause) {
        super("未知錯誤", cause);
    }

    @Override
    public String getErrorCode() {
        return "Unknown";
    }
}
