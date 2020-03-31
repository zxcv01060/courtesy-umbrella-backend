package tw.edu.ntub.imd.databaseconfig.dao.impl;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.NoRepositoryBean;
import tw.edu.ntub.imd.databaseconfig.dao.BaseViewDAO;
import tw.edu.ntub.imd.databaseconfig.dao.criteria.CountQuerySelector;
import tw.edu.ntub.imd.databaseconfig.dao.criteria.PredicateList;
import tw.edu.ntub.imd.databaseconfig.dao.criteria.QuerySelectorImpl;
import tw.edu.ntub.imd.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.utils.bean.JavaBean;
import tw.edu.ntub.imd.utils.bean.JavaBeanField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoRepositoryBean
public class BaseViewDAOImpl<ID, E> implements BaseViewDAO<ID, E> {
    @PersistenceContext
    private EntityManager entityManager;
    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public BaseViewDAOImpl() {
        entityClass = (Class<E>) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), BaseViewDAOImpl.class))[1];
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    protected QuerySelectorImpl<E, E> getQuerySelector() {
        return QuerySelectorImpl.create(entityManager, entityClass);
    }

    protected <R> QuerySelectorImpl<E, R> getQuerySelector(SingularAttribute<E, R> attribute) {
        return QuerySelectorImpl.create(entityManager, entityClass, attribute);
    }

    protected <R> QuerySelectorImpl<E, R> getQuerySelector(Class<R> resultClass) {
        return QuerySelectorImpl.create(entityManager, entityClass, resultClass);
    }

    protected CountQuerySelector<E> getCountQuerySelector() {
        return new CountQuerySelector<>(QuerySelectorImpl.create(entityManager, entityClass, Long.class));
    }

    /**
     * 根據Entity的 {@link javax.persistence.Id}去查詢資料庫對應的Entity
     * <p>
     * Note: 查詢到的Entity在交易完成前的任何賦值都會在完成後更新到資料庫
     *
     * @param id Entity物件的Primary Key({@link javax.persistence.Id})
     * @return 回傳查詢到的Entity
     * @see java.util.Optional
     */
    @Nonnull
    @Override
    public Optional<E> getById(@Nonnull ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Nonnull
    @Override
    public List<E> searchAll() {
        return getQuerySelector().getResultList();
    }

    @Nonnull
    @Override
    public List<E> searchAll(@Nonnull Pager pager) {
        return getQuerySelector().getResultList(pager);
    }

    @Nonnull
    @Override
    public List<E> searchByEntity(@Nullable E e) {
        if (e == null) {
            return Collections.emptyList();
        }
        return createTypedQueryByEntity(e).getResultList();
    }

    private TypedQuery<E> createTypedQueryByEntity(@Nonnull E e) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(entityClass);
        Root<E> from = query.from(entityClass);
        PredicateList predicateList = new PredicateList();
        JavaBean entityJavaBean = new JavaBean(e);
        setRestriction(builder, from, predicateList, entityJavaBean);
        if (predicateList.isNotEmpty()) {
            query.where(predicateList.get());
        }
        return entityManager.createQuery(query);
    }

    private void setRestriction(CriteriaBuilder builder, From<?, ?> from, PredicateList predicateList, JavaBean javaBean) {
        for (JavaBeanField javaBeanField : javaBean.getFieldList()) {
            if (javaBeanField.isNotNull()) {
                if (javaBeanField.isAnnotationPresent(JoinColumn.class)) {
                    Join<E, ?> join = from.join(javaBeanField.getName(), JoinType.INNER);
                    setRestriction(builder, join, predicateList, javaBeanField.getJavaBean());
                } else if (javaBeanField.isAnnotationPresent(Column.class)) {
                    predicateList.add(builder.equal(from.get(javaBeanField.getName()), javaBeanField.getValue()));
                }
            }
        }
    }

    @Nonnull
    @Override
    public Optional<E> getByEntity(@Nullable E e) {
        if (e == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(createTypedQueryByEntity(e).getSingleResult());
    }
}
