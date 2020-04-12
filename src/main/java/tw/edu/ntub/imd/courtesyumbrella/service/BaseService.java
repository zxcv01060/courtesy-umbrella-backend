package tw.edu.ntub.imd.courtesyumbrella.service;

import tw.edu.ntub.imd.courtesyumbrella.bean.BaseBean;

public interface BaseService<B extends BaseBean, ID> extends BaseViewService<B, ID> {
    @SuppressWarnings("UnusedReturnValue")
    B create(B b);

    void update(ID id, B b);

    void delete(ID id);
}
