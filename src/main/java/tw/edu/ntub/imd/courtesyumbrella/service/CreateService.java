package tw.edu.ntub.imd.courtesyumbrella.service;

import tw.edu.ntub.imd.courtesyumbrella.bean.BaseBean;

public interface CreateService<B extends BaseBean> {
    B create(B b);
}
