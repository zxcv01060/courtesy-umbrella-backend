package tw.edu.ntub.imd.utils.bean;

import tw.edu.ntub.imd.exception.NullParameterException;
import tw.edu.ntub.imd.exception.ProjectException;
import tw.edu.ntub.imd.utils.common.ClassUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaBean {
    private Object originalObject;
    private List<JavaBeanField> fieldList = new ArrayList<>();

    public JavaBean(Object object) throws ProjectException {
        if (object == null) {
            throw new NullParameterException("object不能為null");
        }
        this.originalObject = object;
        ClassUtils.ClassWrapper classWrapper = new ClassUtils.ClassWrapper(originalObject.getClass());
        for (ClassUtils.FieldWrapper fieldWrapper : classWrapper.getAllField()) {
            if (!fieldWrapper.isStatic() && !fieldWrapper.isFinal()) {
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
