package tw.edu.ntub.imd.courtesyumbrella.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.courtesyumbrella.service.UserService;
import tw.edu.ntub.imd.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.databaseconfig.entity.User;
import tw.edu.ntub.imd.utils.bean.BeanUtils;

import javax.annotation.Nonnull;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDAO, User, String, UserBean> implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        super(userDAO, new UserBeanEntityTransformer());
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserBean create(UserBean userBean) {
        User user = toEntity(userBean);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setModifyId(user.getAccount());
        user = userDAO.saveAndFlush(user);
        userBean.setPassword(user.getPassword());
        userBean.setCreateDate(user.getCreateDate());
        userBean.setModifyId(user.getModifyId());
        userBean.setModifyDate(user.getModifyDate());
        return userBean;
    }

    private static class UserBeanEntityTransformer implements BeanEntityTransformer<User, UserBean> {

        @Nonnull
        @Override
        public User toEntity(@Nonnull UserBean userBean) {
            return BeanUtils.copy(userBean, new User());
        }

        @Nonnull
        @Override
        public UserBean toBean(@Nonnull User user) {
            return BeanUtils.copy(user, new UserBean());
        }
    }
}
