package tw.edu.ntub.imd.exception;

public class NullParameterException extends ProjectException {
    public NullParameterException() {
        super("輸入參數為 null");
    }

    public NullParameterException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "Parameter - Null";
    }

}
