package tw.edu.ntub.imd.courtesyumbrella.service.transformer;

import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.birc.common.util.bean.BeanUtils;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.databaseconfig.entity.User;

import javax.annotation.Nonnull;

@Component
public class UserBeanEntityTransformer implements BeanEntityTransformer<User, UserBean> {
    @Nonnull
    @Override
    public User transferToEntity(@Nonnull UserBean userBean) {
        return BeanUtils.copy(userBean, new User());
    }

    @Nonnull
    @Override
    public UserBean transferToBean(@Nonnull User user) {
        return BeanUtils.copy(user, new UserBean());
    }
}
