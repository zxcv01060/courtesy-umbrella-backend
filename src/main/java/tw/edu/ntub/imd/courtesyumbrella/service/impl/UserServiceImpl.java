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
import javax.annotation.PostConstruct;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDAO, String, User, UserBean> implements UserService {
    private UserDAO userDAO;
    private BeanEntityTransformer<User, UserBean> beanBeanEntityTransformer;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        super(userDAO, new UserBeanEntityTransformer());
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setBeanBeanEntityTransformer() {
        this.beanBeanEntityTransformer = super.getBeanEntityTransformer();
    }

    @Override
    public void create(UserBean userBean) {
        User user = beanBeanEntityTransformer.toEntity(userBean);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveAndFlush(user);
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
