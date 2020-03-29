package tw.edu.ntub.imd.exception;

public class NotFoundMethodException extends ProjectException {
    public NotFoundMethodException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "Method - NotFound";
    }
}
