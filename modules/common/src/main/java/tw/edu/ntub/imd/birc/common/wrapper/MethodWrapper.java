package tw.edu.ntub.imd.birc.common.wrapper;

import tw.edu.ntub.imd.birc.common.exception.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodWrapper {
    private final Object target;
    private final Method method;
    private final int parameterCount;

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
        return (method.getName()
                .startsWith("get") || method.getName()
                .startsWith("is")) &&
                parameterCount == 0 &&
                getterReturnType.isAssignableFrom(method.getReturnType());
    }

    public boolean isSetter(Class<?> setterParameterType) {
        return method.getName()
                .startsWith("set") &&
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
