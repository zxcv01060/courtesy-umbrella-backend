package tw.edu.ntub.imd.birc.common.wrapper;


import tw.edu.ntub.imd.birc.common.exception.AnnotationNotDeclareException;
import tw.edu.ntub.imd.birc.common.exception.ModifierPrivateException;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldWrapper {
    private final Object fieldSourceObject;
    private final Field field;

    public FieldWrapper(Field field) throws NullParameterException {
        this(null, field);
    }

    public FieldWrapper(Object fieldSourceObject, Field field) throws NullParameterException {
        if (field == null) {
            throw new NullParameterException();
        }
        this.fieldSourceObject = fieldSourceObject;
        this.field = field;
    }

    public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }

    public boolean isFinal() {
        return Modifier.isFinal(field.getModifiers());
    }

    public Class<?> getType() {
        return field.getType();
    }

    public String getName() {
        return field.getName();
    }

    public Object getValue() throws NullParameterException, ModifierPrivateException {
        return getValue(fieldSourceObject);
    }

    public Object getValue(Object fieldSourceObject) throws NullParameterException, ModifierPrivateException {
        if (fieldSourceObject == null) {
            throw new NullParameterException();
        }
        try {
            return field.get(fieldSourceObject);
        } catch (IllegalAccessException e) {
            throw new ModifierPrivateException(field.getName());
        }
    }

    public void setValue(Object value) throws NullParameterException, ModifierPrivateException {
        setValue(fieldSourceObject, value);
    }

    public void setValue(Object fieldSourceObject, Object value) throws NullParameterException, ModifierPrivateException {
        if (fieldSourceObject == null) {
            throw new NullParameterException();
        }
        try {
            field.set(fieldSourceObject, value);
        } catch (IllegalAccessException e) {
            throw new ModifierPrivateException(field.getName());
        }
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return field.isAnnotationPresent(annotationClass);
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) throws AnnotationNotDeclareException {
        if (isAnnotationPresent(annotationClass)) {
            return field.getAnnotation(annotationClass);
        } else {
            throw new AnnotationNotDeclareException(annotationClass);
        }
    }

    public Field get() {
        return field;
    }
}
