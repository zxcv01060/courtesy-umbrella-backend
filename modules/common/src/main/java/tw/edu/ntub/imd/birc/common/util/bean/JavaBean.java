package tw.edu.ntub.imd.birc.common.util.bean;

import tw.edu.ntub.imd.birc.common.annotation.CopyIgnore;
import tw.edu.ntub.imd.birc.common.exception.NullParameterException;
import tw.edu.ntub.imd.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.birc.common.wrapper.ClassWrapper;
import tw.edu.ntub.imd.birc.common.wrapper.FieldWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaBean {
    private final Object originalObject;
    private final List<JavaBeanField> fieldList = new ArrayList<>();

    public JavaBean(Object object) throws ProjectException {
        if (object == null) {
            throw new NullParameterException("object不能為null");
        }
        this.originalObject = object;
        ClassWrapper classWrapper = new ClassWrapper(originalObject.getClass());
        for (FieldWrapper fieldWrapper : classWrapper.getAllField()) {
            if (!fieldWrapper.isStatic() && !fieldWrapper.isFinal() && !fieldWrapper.isAnnotationPresent(CopyIgnore.class)) {
                fieldList.add(new JavaBeanField(object, fieldWrapper));
            }
        }
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    public List<JavaBeanField> getFieldList() {
        return Collections.unmodifiableList(fieldList);
    }
}
