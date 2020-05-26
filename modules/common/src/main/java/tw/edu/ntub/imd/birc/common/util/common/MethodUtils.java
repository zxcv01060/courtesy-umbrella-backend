package tw.edu.ntub.imd.birc.common.util.common;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.NotFoundMethodException;
import tw.edu.ntub.imd.birc.common.wrapper.FieldWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
@SuppressWarnings("unused")
public class MethodUtils {

    public Method getFieldGetter(FieldWrapper fieldWrapper) throws NotFoundMethodException {
        return getFieldGetter(fieldWrapper.get());
    }

    public Method getFieldGetter(Field field) throws NotFoundMethodException {
        Class<?> fieldDeclaringClass = field.getDeclaringClass();
        String getterPostfix = field.getName()
                .substring(0, 1)
                .toUpperCase() + field.getName()
                .substring(1);
        return ClassUtils.getAllMethods(fieldDeclaringClass, false, false)
                .parallelStream()
                .filter(method -> {
                    String name = method.getName();
                    return method.getParameterCount() == 0 && (
                            name.equals("get" + getterPostfix) ||
                                    name.equals("get" + field.getName()) ||
                                    name.equals("is" + getterPostfix) ||
                                    name.equals("is" + field.getName())
                    );
                })
                .findFirst()
                .orElseThrow(() -> new NotFoundMethodException("找不到對應的Getter: " + field.getName()));
    }

    public Method getFieldSetter(FieldWrapper fieldWrapper) throws NotFoundMethodException {
        return getFieldSetter(fieldWrapper.get());
    }

    public Method getFieldSetter(Field field) throws NotFoundMethodException {
        Class<?> fieldDeclaringClass = field.getDeclaringClass();
        String getterPostfix = field.getName()
                .substring(0, 1)
                .toUpperCase() + field.getName()
                .substring(1);
        return ClassUtils.getAllMethods(fieldDeclaringClass, false, false)
                .parallelStream()
                .filter(method -> {
                    String name = method.getName();
                    return method.getParameterCount() == 1 &&
                            field.getType()
                                    .equals(method.getParameterTypes()[0]) &&
                            (name.equals("set" + getterPostfix) || name.equals("set" + field.getName()));
                })
                .findFirst()
                .orElseThrow(() -> new NotFoundMethodException("找不到對應的Setter: " + field.getName()));
    }
}
