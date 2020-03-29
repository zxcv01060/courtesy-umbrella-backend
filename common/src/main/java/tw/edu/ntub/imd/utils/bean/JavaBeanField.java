package tw.edu.ntub.imd.utils.bean;

import tw.edu.ntub.imd.annotation.CopyIgnore;
import tw.edu.ntub.imd.exception.AnnotationNotDeclareException;
import tw.edu.ntub.imd.exception.ClassMismatchException;
import tw.edu.ntub.imd.exception.ProjectException;
import tw.edu.ntub.imd.utils.bean.convert.CopyConverter;
import tw.edu.ntub.imd.utils.common.ClassUtils;
import tw.edu.ntub.imd.utils.common.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JavaBeanField {
    private ClassUtils.FieldWrapper fieldWrapper;
    private ClassUtils.ClassWrapper classWrapper;
    private MethodUtils.MethodWrapper getter;
    private MethodUtils.MethodWrapper setter;
    private JavaBean javaBean;

    public JavaBeanField(Object originalObject, ClassUtils.FieldWrapper fieldWrapper) throws ProjectException {
        this.fieldWrapper = fieldWrapper;
        classWrapper = new ClassUtils.ClassWrapper(fieldWrapper.getType());
        this.getter = new MethodUtils.MethodWrapper(originalObject, MethodUtils.getFieldGetter(fieldWrapper));
        this.setter = new MethodUtils.MethodWrapper(originalObject, MethodUtils.getFieldSetter(fieldWrapper));
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
        return fieldWrapper.getType().getName().startsWith("tw");
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
