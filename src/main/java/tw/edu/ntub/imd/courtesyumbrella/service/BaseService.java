package tw.edu.ntub.imd.courtesyumbrella.service;

import tw.edu.ntub.imd.courtesyumbrella.bean.BaseBean;

public interface BaseService<B extends BaseBean, ID> extends BaseViewService<B, ID> {
    B createAndReturnBean(B bean);

    void update(ID id, B bean);

    void delete(ID id);
}
