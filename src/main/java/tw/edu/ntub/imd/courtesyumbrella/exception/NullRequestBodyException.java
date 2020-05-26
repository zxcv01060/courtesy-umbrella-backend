package tw.edu.ntub.imd.courtesyumbrella.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import tw.edu.ntub.imd.birc.common.exception.ProjectException;

public class NullRequestBodyException extends ProjectException {

    public NullRequestBodyException(HttpMessageNotReadableException cause) {
        super("RequestBody為null", cause);
    }

    @Override
    public String getErrorCode() {
        return "Http - NullRequestBody";
    }
}
