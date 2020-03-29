package tw.edu.ntub.imd.databaseconfig.dao.impl;

import org.springframework.data.repository.NoRepositoryBean;
import tw.edu.ntub.imd.databaseconfig.dao.BaseDAO;
import tw.edu.ntub.imd.databaseconfig.dto.Pager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@NoRepositoryBean
public class BaseDAOImpl<E> implements BaseDAO<E> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public E insert(E e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    public void delete(E e) {
        entityManager.remove(e);
    }

    @Override
    public void update(E e) {
        entityManager.merge(e);
    }

    @Override
    public List<E> searchAll() {
        return null;
    }

    @Override
    public List<E> searchAll(Pager pager) {
        return null;
    }
}
