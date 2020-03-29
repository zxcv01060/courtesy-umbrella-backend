package tw.edu.ntub.imd.exception;

public class PermissionDeniedException extends ProjectException {
    public PermissionDeniedException() {
        super("權限不足");
    }

    @Override
    public String getErrorCode() {
        return "User - PermissionDenied";
    }
}
