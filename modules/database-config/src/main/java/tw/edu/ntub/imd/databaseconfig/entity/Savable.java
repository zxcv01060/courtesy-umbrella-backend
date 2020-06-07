package tw.edu.ntub.imd.databaseconfig.entity;

import org.springframework.data.domain.Persistable;

import javax.annotation.Nullable;
import java.io.Serializable;

public interface Savable<ID extends Serializable> extends Persistable<ID> {
    @Override
    @Nullable
    ID getId();

    default void isNewEntity() {
        setNew(true);
    }

    default void isOldEntity() {
        setNew(false);
    }

    void setNew(boolean isNew);

    default boolean isSave() {
        return isNew();
    }

    default boolean isUpdate() {
        return !isNew();
    }

    @Override
    boolean isNew();
}
