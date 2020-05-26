package tw.edu.ntub.imd.birc.common.util.bean;

import org.apache.commons.lang3.ArrayUtils;
import tw.edu.ntub.imd.birc.common.annotation.CopyTo;
import tw.edu.ntub.imd.birc.common.exception.AnnotationNotDeclareException;

public class CopyDescriptor {
    private String[] nameArray;
    private String[] toNameArray;

    public CopyDescriptor(JavaBeanField javaBeanField) {
        try {
            CopyTo copyTo = javaBeanField.getAnnotation(CopyTo.class);
            nameArray = copyTo.name().length != 0 ? ArrayUtils.insert(0, copyTo.name(), javaBeanField.getName()) : new String[]{javaBeanField.getName()};
            toNameArray = copyTo.to().length != 0 ? ArrayUtils.insert(0, copyTo.to(), javaBeanField.getName()) : new String[]{javaBeanField.getName()};
        } catch (AnnotationNotDeclareException e) {
            nameArray = new String[]{javaBeanField.getName()};
            toNameArray = new String[]{javaBeanField.getName()};
        }
    }

    public String[] getNameArray() {
        return nameArray;
    }

    public String[] getToNameArray() {
        return toNameArray;
    }
}
