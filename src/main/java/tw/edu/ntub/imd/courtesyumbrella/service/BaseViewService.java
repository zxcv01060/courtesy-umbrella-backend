package tw.edu.ntub.imd.courtesyumbrella.service;

import tw.edu.ntub.imd.courtesyumbrella.bean.BaseBean;
import tw.edu.ntub.imd.databaseconfig.dto.Pager;

import java.util.List;
import java.util.Optional;

public interface BaseViewService<B extends BaseBean, ID> {
    Optional<B> getById(ID id);

    List<B> searchAll();

    List<B> searchAll(Pager pager);

    List<B> searchByBean(B b);

    Optional<B> getByBean(B b);
}
