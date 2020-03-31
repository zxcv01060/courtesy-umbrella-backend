package tw.edu.ntub.imd.databaseconfig.dao.impl;

import org.springframework.data.repository.NoRepositoryBean;
import tw.edu.ntub.imd.databaseconfig.dao.BaseDAO;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

@NoRepositoryBean
public class BaseDAOImpl<ID, E> extends BaseViewDAOImpl<ID, E> implements BaseDAO<ID, E> {
    @Nonnull
    @Override
    public E insert(@Nonnull E e) {
        EntityManager entityManager = getEntityManager();
        entityManager.persist(e);
        entityManager.flush();
        return e;
    }

    @Override
    public void delete(@Nonnull E e) {
        EntityManager entityManager = getEntityManager();
        entityManager.remove(e);
        entityManager.flush();
    }

    @Override
    public void update(@Nonnull E e) {
        EntityManager entityManager = getEntityManager();
        entityManager.merge(e);
        entityManager.flush();
    }
}
