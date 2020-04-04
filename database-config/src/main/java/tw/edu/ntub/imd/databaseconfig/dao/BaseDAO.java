package tw.edu.ntub.imd.databaseconfig.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface BaseDAO<ID, E> extends BaseViewDAO<ID, E>, JpaRepository<E, ID> {
    @Nonnull
    @Override
    Optional<E> findById(@Nonnull ID id);
}
