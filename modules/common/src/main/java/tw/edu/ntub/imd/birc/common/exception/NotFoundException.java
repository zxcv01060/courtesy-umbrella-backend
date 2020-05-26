package tw.edu.ntub.imd.birc.common.exception;

public class NotFoundException extends ProjectException {
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "Search - NotFound";
    }
}
