package tw.edu.ntub.imd.courtesyumbrella.exception.form;

import lombok.Getter;
import org.springframework.validation.FieldError;
import tw.edu.ntub.imd.birc.common.exception.ProjectException;

@Getter
public class InvalidFormException extends ProjectException {
    public InvalidFormException(FieldError error) {
        super(error.getDefaultMessage());
    }

    @Override
    public String getErrorCode() {
        return "FormValidation - Invalid";
    }
}
