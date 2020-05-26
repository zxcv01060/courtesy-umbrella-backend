package tw.edu.ntub.imd.birc.common.wrapper;

import tw.edu.ntub.imd.birc.common.exception.*;
import tw.edu.ntub.imd.birc.common.util.common.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClassWrapper {
    private final Class<?> target;

    public ClassWrapper(Class<?> target) throws NullParameterException {
        if (target == null) {
            throw new NullParameterException();
        }
        this.target = target;
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return target.isAnnotationPresent(annotationClass);
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) throws AnnotationNotDeclareException {
        if (isAnnotationPresent(annotationClass)) {
            return target.getAnnotation(annotationClass);
        } else {
            throw new AnnotationNotDeclareException(annotationClass);
        }
    }

    public Object newInstance(Object... parameters) throws ProjectException {
        Class<?>[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        try {
            Constructor<?> constructor = target.getConstructor(parameterTypes);
            return constructor.newInstance(parameters);
        } catch (NoSuchMethodException e) {
            throw new NotFoundMethodException("無此建構元：" + Arrays.toString(parameterTypes));
        } catch (IllegalAccessException e) {
            throw new ModifierPrivateException(target.getName() + "#Constructor(" + Arrays.toString(parameterTypes) + ")");
        } catch (InvocationTargetException e) {
            throw new MethodIsNotInTargetException(target, parameterTypes);
        } catch (InstantiationException e) {
            throw new UnknownException(e);
        }
    }

    public String getName() {
        return target.getName();
    }

    public String getPackageName() {
        return target.getPackageName();
    }

    public boolean isSuperClass(Class<?> extendClass) {
        return ClassUtils.isSuperClass(target, extendClass);
    }

    public boolean isChildrenClass(Class<?> superClass) {
        return ClassUtils.isChildrenClass(target, superClass);
    }

    public Class<?> get() {
        return target;
    }

    public List<FieldWrapper> getAllField() throws NullParameterException {
        return ClassUtils.getAllFieldAndContainSuperClass(target);
    }

    public Collection<Method> getAllMethod(boolean includeAllPackageAndPrivateMethodsOfSuperclasses,
                                           boolean includeOverridenAndHidden) {
        return ClassUtils.getAllMethods(target, includeAllPackageAndPrivateMethodsOfSuperclasses, includeOverridenAndHidden);
    }
}
