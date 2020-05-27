package tw.edu.ntub.imd.courtesyumbrella.util.response;

public class ResponseData {
    private final boolean success;
    private final String errorCode;
    private final String message;

    public ResponseData(boolean success, String errorCode, String message) {
        this.success = success;
        this.errorCode = errorCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}

