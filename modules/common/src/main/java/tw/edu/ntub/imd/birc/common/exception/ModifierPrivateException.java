package tw.edu.ntub.imd.birc.common.exception;

public class ModifierPrivateException extends ProjectException {
    public ModifierPrivateException(String name) {
        super(name + "的修飾詞為private，請呼叫java.lang.reflect.Method/java.lang.reflect.Field#setAccessible(true)");
    }

    @Override
    public String getErrorCode() {
        return "Modifier - PrivateError";
    }
}
