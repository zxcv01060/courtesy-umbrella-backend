package tw.edu.ntub.imd.birc.common.util.bean;

import tw.edu.ntub.imd.birc.common.annotation.CopyIgnore;
import tw.edu.ntub.imd.birc.common.exception.AnnotationNotDeclareException;
import tw.edu.ntub.imd.birc.common.exception.ClassMismatchException;
import tw.edu.ntub.imd.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.birc.common.util.bean.convert.CopyConverter;
import tw.edu.ntub.imd.birc.common.util.common.MethodUtils;
import tw.edu.ntub.imd.birc.common.wrapper.ClassWrapper;
import tw.edu.ntub.imd.birc.common.wrapper.FieldWrapper;
import tw.edu.ntub.imd.birc.common.wrapper.MethodWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JavaBeanField {
    private final FieldWrapper fieldWrapper;
    private final ClassWrapper classWrapper;
    private final MethodWrapper getter;
    private final MethodWrapper setter;
    private JavaBean javaBean;

    public JavaBeanField(Object originalObject, FieldWrapper fieldWrapper) throws ProjectException {
        this.fieldWrapper = fieldWrapper;
        classWrapper = new ClassWrapper(fieldWrapper.getType());
        this.getter = new MethodWrapper(originalObject, MethodUtils.getFieldGetter(fieldWrapper));
        this.setter = new MethodWrapper(originalObject, MethodUtils.getFieldSetter(fieldWrapper));
        if (!fieldWrapper.isAnnotationPresent(CopyIgnore.class) && isJavaBean() && getValue() != null) {
            javaBean = new JavaBean(getValue());
        }
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return fieldWrapper.isAnnotationPresent(annotationClass) || getter.isAnnotationPresent(annotationClass);
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) throws AnnotationNotDeclareException {
        if (fieldWrapper.isAnnotationPresent(annotationClass)) {
            return fieldWrapper.getAnnotation(annotationClass);
        } else {
            return getter.getAnnotation(annotationClass);
        }
    }

    public String getName() {
        return fieldWrapper.getName();
    }

    public Object getValue() throws ProjectException {
        return getter.invoke();
    }

    public boolean canSetValue(JavaBeanField javaBeanField) {
        return javaBeanField != null && classWrapper.isChildrenClass(javaBeanField.fieldWrapper.getType());
    }

    public void setValue(JavaBeanField javaBeanField) throws ProjectException {
        setValue(javaBeanField.getValue());
    }

    public void setValue(Object value) throws ProjectException {
        if (classWrapper.isSuperClass(value.getClass())) {
            setter.invoke(value);
        } else if (isJavaBean()) {
            Object fieldValue = getValue();
            if (fieldValue == null) {
                fieldValue = classWrapper.newInstance();
            }
            BeanUtils.copy(value, fieldValue);
            setValue(fieldValue);
        } else {
            throw new ClassMismatchException(classWrapper.get(), value.getClass());
        }
    }

    public void setValue(Object value, CopyConverter<Object, ?> copyConverter) throws ProjectException {
        if (copyConverter != null) {
            setValue(copyConverter.convert(value));
        } else {
            setValue(value);
        }
    }

    public JavaBean getJavaBean() throws ProjectException {
        if (javaBean == null) {
            javaBean = new JavaBean(classWrapper.newInstance());
        }
        return javaBean;
    }

    public boolean isJavaBean() {
        return fieldWrapper.getType()
                .getName()
                .startsWith("tw");
    }

    public boolean isNotNull() throws ProjectException {
        return getValue() != null;
    }

    public Field getField() {
        return fieldWrapper.get();
    }

    public Method getGetter() {
        return getter.get();
    }

    public Method getSetter() {
        return setter.get();
    }
}
