package tw.edu.ntub.imd.courtesyumbrella.exception.form;

import tw.edu.ntub.imd.birc.common.exception.ProjectException;

public class InvalidRequestFormatException extends ProjectException {
    public InvalidRequestFormatException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "FormValidation - Invalid";
    }
}
