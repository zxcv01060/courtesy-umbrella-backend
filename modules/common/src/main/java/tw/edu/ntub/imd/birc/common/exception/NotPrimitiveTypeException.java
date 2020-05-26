package tw.edu.ntub.imd.birc.common.exception;

public class NotPrimitiveTypeException extends ProjectException {
    public NotPrimitiveTypeException(Class<?> target) {
        super(target.getName() + "不是基本資料型態的包裝類型");
    }

    @Override
    public String getErrorCode() {
        return "Class - NotPrimitiveType";
    }
}
