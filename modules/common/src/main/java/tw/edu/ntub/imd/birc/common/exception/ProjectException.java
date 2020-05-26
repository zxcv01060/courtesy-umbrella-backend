package tw.edu.ntub.imd.birc.common.exception;

public abstract class ProjectException extends RuntimeException {
    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getErrorCode();
}
