package tw.edu.ntub.imd.databaseconfig.dao;

import tw.edu.ntub.imd.databaseconfig.dto.Pager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface BaseViewDAO<ID, E> {
    @Nonnull
    Optional<E> getById(@Nonnull ID id);

    @Nonnull
    List<E> searchAll();

    @Nonnull
    List<E> searchAll(@Nonnull Pager pager);

    @Nonnull
    List<E> searchByEntity(@Nullable E e);

    @Nonnull
    Optional<E> getByEntity(@Nullable E e);
}
