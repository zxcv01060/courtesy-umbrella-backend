package tw.edu.ntub.imd.databaseconfig.exception;

import tw.edu.ntub.imd.birc.common.exception.ProjectException;

public class RepeatPersistException extends ProjectException {
    public RepeatPersistException(Object entity) {
        this("Entity重複新增：" + entity);
    }

    public RepeatPersistException(String message) {
        super(message);
    }

    public RepeatPersistException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getErrorCode() {
        return "DAO - RepeatPersist";
    }
}
