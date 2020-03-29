package tw.edu.ntub.imd.exception;

public class UnknownException extends ProjectException {
    public UnknownException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrorCode() {
        return "Unknown";
    }
}
