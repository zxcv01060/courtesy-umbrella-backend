package tw.edu.ntub.imd.exception;

public class ClassMismatchException extends ProjectException {
    public ClassMismatchException(Class<?> source, Class<?> target) {
        super(source.getName() + "不是一種" + target.getName());
    }

    @Override
    public String getErrorCode() {
        return "Class - CastError";
    }
}
