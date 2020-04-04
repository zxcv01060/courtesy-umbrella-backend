package tw.edu.ntub.imd.courtesyumbrella.exception;

import tw.edu.ntub.imd.exception.ProjectException;

public abstract class RequiredParameterException extends ProjectException {
    private String requiredParameterName;

    public RequiredParameterException(String requiredParameterName) {
        super("缺少必要參數：" + requiredParameterName);
        this.requiredParameterName = requiredParameterName;
    }

    @Override
    public String getErrorCode() {
        return "Required - " + requiredParameterName;
    }
}
