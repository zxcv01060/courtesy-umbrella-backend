package tw.edu.ntub.imd.birc.common.exception;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodIsNotInTargetException extends ProjectException {

    public MethodIsNotInTargetException(Class<?> constructorClass, Class<?>... parameterTypes) {
        super(constructorClass.getName() + "內並無對應的建構元：" + Arrays.toString(parameterTypes));
    }

    public MethodIsNotInTargetException(Object target, Method method) {
        super(method.getName() + "不為" + target.toString() + "的方法");
    }

    @Override
    public String getErrorCode() {
        return "Invoke - MethodTarget";
    }
}
