package tw.edu.ntub.imd.utils.common;

import tw.edu.ntub.imd.exception.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodUtils {
    private MethodUtils() {

    }

    public static Method getFieldGetter(ClassUtils.FieldWrapper fieldWrapper) throws NotFoundMethodException {
        return getFieldGetter(fieldWrapper.get());
    }

    public static Method getFieldGetter(Field field) throws NotFoundMethodException {
        Class<?> fieldDeclaringClass = field.getDeclaringClass();
        String getterPostfix = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
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

    public static Method getFieldSetter(ClassUtils.FieldWrapper fieldWrapper) throws NotFoundMethodException {
        return getFieldSetter(fieldWrapper.get());
    }

    public static Method getFieldSetter(Field field) throws NotFoundMethodException {
        Class<?> fieldDeclaringClass = field.getDeclaringClass();
        String getterPostfix = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        return ClassUtils.getAllMethods(fieldDeclaringClass, false, false)
                .parallelStream()
                .filter(method -> {
                    String name = method.getName();
                    return method.getParameterCount() == 1 &&
                            field.getType().equals(method.getParameterTypes()[0]) &&
                            (name.equals("set" + getterPostfix) || name.equals("set" + field.getName()));
                })
                .findFirst()
                .orElseThrow(() -> new NotFoundMethodException("找不到對應的Setter: " + field.getName()));
    }

    public static class MethodWrapper {
        private Object target;
        private Method method;
        private int parameterCount;

        public MethodWrapper(Object target, Method method) throws NullParameterException {
            if (target == null || method == null) {
                throw new NullParameterException();
            }
            this.target = target;
            this.method = method;
            parameterCount = method.getParameterCount();
        }

        public Object invoke(Object... parameters) throws ProjectException {
            try {
                return method.invoke(target, parameters);
            } catch (IllegalAccessException e) {
                throw new ModifierPrivateException(method.getName());
            } catch (InvocationTargetException e) {
                throw new MethodIsNotInTargetException(target, method);
            } catch (IllegalArgumentException e) {
                throw new UnknownException(e);
            }
        }

        public boolean isGetter(Class<?> getterReturnType) {
            return (method.getName().startsWith("get") || method.getName().startsWith("is")) &&
                    parameterCount == 0 &&
                    getterReturnType.isAssignableFrom(method.getReturnType());
        }

        public boolean isSetter(Class<?> setterParameterType) {
            return method.getName().startsWith("set") &&
                    parameterCount == 1 &&
                    setterParameterType.isAssignableFrom(method.getParameterTypes()[0]);
        }

        public <A extends Annotation> A getAnnotation(Class<A> annotationClass) throws AnnotationNotDeclareException {
            if (isAnnotationPresent(annotationClass)) {
                return method.getAnnotation(annotationClass);
            } else {
                throw new AnnotationNotDeclareException(annotationClass);
            }
        }

        public <A extends Annotation> boolean isAnnotationPresent(Class<A> annotationClass) {
            return method.isAnnotationPresent(annotationClass);
        }

        public Method get() {
            return method;
        }
    }

    public static class MethodIsNotInTargetException extends ProjectException {

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
}
