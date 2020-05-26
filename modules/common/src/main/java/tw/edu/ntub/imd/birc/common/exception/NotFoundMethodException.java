package tw.edu.ntub.imd.birc.common.exception;

public class NotFoundMethodException extends ProjectException {
    public NotFoundMethodException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "Method - NotFound";
    }
}
