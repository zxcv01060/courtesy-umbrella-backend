package tw.edu.ntub.imd.databaseconfig.dao;

import tw.edu.ntub.imd.databaseconfig.dto.Pager;

import java.util.List;

public interface BaseDAO<E> {
    E insert(E e);

    void delete(E e);

    void update(E e);

    List<E> searchAll();

    List<E> searchAll(Pager pager);
}
