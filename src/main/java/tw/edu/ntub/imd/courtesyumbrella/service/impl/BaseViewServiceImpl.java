package tw.edu.ntub.imd.courtesyumbrella.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import tw.edu.ntub.imd.courtesyumbrella.bean.BaseBean;
import tw.edu.ntub.imd.courtesyumbrella.service.BaseViewService;
import tw.edu.ntub.imd.databaseconfig.dao.BaseViewDAO;
import tw.edu.ntub.imd.databaseconfig.dto.Pager;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseViewServiceImpl<D extends BaseViewDAO<E, ID>, E, ID extends Serializable, B extends BaseBean> implements BaseViewService<B, ID> {
    protected D baseDAO;
    private BeanEntityTransformer<E, B> beanEntityTransformer;

    public BaseViewServiceImpl(D d, BeanEntityTransformer<E, B> beanEntityTransformer) {
        Assert.notNull(d, "baseDAO不能為null");
        Assert.notNull(d, "beanEntityTransformer不能為null");
        this.baseDAO = d;
        this.beanEntityTransformer = beanEntityTransformer;
    }

    @Nonnull
    protected B toBean(@Nonnull E e) {
        return beanEntityTransformer.toBean(e);
    }

    @Nonnull
    protected List<B> toBeanList(@Nonnull List<E> eList) {
        return beanEntityTransformer.toBeanList(eList);
    }

    @Nonnull
    protected E toEntity(@Nonnull B b) {
        return beanEntityTransformer.toEntity(b);
    }

    @Nonnull
    protected List<E> toEntityList(@Nonnull List<B> bList) {
        return beanEntityTransformer.toEntityList(bList);
    }

    @Override
    public Optional<B> getById(ID id) {
        Optional<E> optional = baseDAO.findById(id);
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
        return toBeanList(baseDAO.findAll());
    }

    @Override
    public List<B> searchAll(Pager pager) {
        PageRequest pageRequest = PageRequest.of(pager.getPage(), pager.getCount());
        return toBeanList(baseDAO.findAll(pageRequest).getContent());
    }

    @Override
    public List<B> searchByBean(B b) {
        List<E> eList = baseDAO.findAll(Example.of(beanEntityTransformer.toEntity(b)));
        return toBeanList(eList);
    }

    @Override
    public Optional<B> getByBean(B b) {
        Optional<E> optional = baseDAO.findOne(Example.of(beanEntityTransformer.toEntity(b)));
        return optional.map(beanEntityTransformer::toBean);
    }

    protected BeanEntityTransformer<E, B> getBeanEntityTransformer() {
        return beanEntityTransformer;
    }

    public interface BeanEntityTransformer<E, B> {
        @Nonnull
        E toEntity(@Nonnull B b);

        @Nonnull
        default List<E> toEntityList(@Nonnull List<B> bList) {
            return bList.stream().filter(Objects::nonNull).map(this::toEntity).collect(Collectors.toList());
        }

        @Nonnull
        B toBean(@Nonnull E e);

        @Nonnull
        default List<B> toBeanList(@Nonnull List<E> eList) {
            return eList.stream().filter(Objects::nonNull).map(this::toBean).collect(Collectors.toList());
        }
    }
}
