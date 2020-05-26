package tw.edu.ntub.imd.birc.common.exception;

import java.lang.annotation.Annotation;

public class AnnotationNotDeclareException extends ProjectException {

    public AnnotationNotDeclareException(Class<? extends Annotation> annotationClass) {
        super("未宣告Annotation：" + annotationClass.getName());
    }

    @Override
    public String getErrorCode() {
        return "Annotation - NotDeclared";
    }
}
