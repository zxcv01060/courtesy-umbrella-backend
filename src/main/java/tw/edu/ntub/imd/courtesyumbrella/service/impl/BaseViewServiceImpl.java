package tw.edu.ntub.imd.courtesyumbrella.service.impl;

import org.springframework.util.Assert;
import tw.edu.ntub.imd.courtesyumbrella.bean.BaseBean;
import tw.edu.ntub.imd.courtesyumbrella.service.BaseViewService;
import tw.edu.ntub.imd.databaseconfig.dao.BaseViewDAO;
import tw.edu.ntub.imd.databaseconfig.dto.Pager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseViewServiceImpl<D extends BaseViewDAO<ID, E>, ID, E, B extends BaseBean> implements BaseViewService<B, ID> {
    protected D baseDAO;
    private BeanEntityTransformer<E, B> beanEntityTransformer;

    public BaseViewServiceImpl(D d, BeanEntityTransformer<E, B> beanEntityTransformer) {
        Assert.notNull(d, "baseDAO不能為null");
        Assert.notNull(d, "beanEntityTransformer不能為null");
        this.baseDAO = d;
        this.beanEntityTransformer = beanEntityTransformer;
    }

    List<E> createVOList(List<B> bList) {
        List<E> voList = new ArrayList<>();
        if (bList == null) {
            return voList;
        }
        for (B b : bList) {
            E vo = beanEntityTransformer.toEntity(b);
            voList.add(vo);
        }
        return voList;
    }

    List<B> createBeanList(List<E> eList) {
        List<B> beanList = new ArrayList<>();
        if (eList == null) {
            return beanList;
        }
        for (E e : eList) {
            B bean = beanEntityTransformer.toBean(e);
            beanList.add(bean);
        }
        return beanList;
    }

    @Override
    public Optional<B> getById(ID id) {
        Optional<E> optional = baseDAO.getById(id);
        if (optional.isPresent()) {
            E entity = optional.get();
            B bean = beanEntityTransformer.toBean(entity);
            return Optional.of(bean);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<B> searchAll() {
        return createBeanList(baseDAO.searchAll());
    }

    @Override
    public List<B> searchAllWithPage(Pager pager) {
        return createBeanList(baseDAO.searchAll(pager));
    }

    @Override
    public List<B> searchByBean(B b) {
        return createBeanList(baseDAO.searchByEntity(beanEntityTransformer.toEntity(b)));
    }

    @Override
    public Optional<B> getByBean(B b) {
        Optional<E> optional = baseDAO.getByEntity(beanEntityTransformer.toEntity(b));
        return optional.map(beanEntityTransformer::toBean);
    }

    public interface BeanEntityTransformer<E, B> {
        @Nonnull
        E toEntity(@Nonnull B b);

        @Nonnull
        B toBean(@Nonnull E e);
    }
}
