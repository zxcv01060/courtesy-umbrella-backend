package tw.edu.ntub.imd.databaseconfig.dao;

import javax.annotation.Nonnull;

public interface BaseDAO<ID, E> extends BaseViewDAO<ID, E> {
    @SuppressWarnings("UnusedReturnValue")
    @Nonnull
    E insert(@Nonnull E e);

    void delete(@Nonnull E e);

    void update(@Nonnull E e);
}
