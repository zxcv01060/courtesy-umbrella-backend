package tw.edu.ntub.imd.courtesyumbrella.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.courtesyumbrella.service.UserService;
import tw.edu.ntub.imd.courtesyumbrella.service.transformer.BeanEntityTransformer;
import tw.edu.ntub.imd.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.databaseconfig.entity.User;
import tw.edu.ntub.imd.databaseconfig.exception.RepeatPersistException;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDAO, User, String, UserBean> implements UserService {
    private final UserDAO userDAO;
    private final BeanEntityTransformer<User, UserBean> transformer;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserDAO userDAO,
            BeanEntityTransformer<User, UserBean> transformer,
            PasswordEncoder passwordEncoder
    ) {
        super(userDAO, transformer);
        this.userDAO = userDAO;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserBean create(UserBean userBean) {
        try {
            User user = transformer.transferToEntity(userBean);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setModifyId(user.getAccount());
            user.setSave(true);
            user = userDAO.saveAndFlush(user);
            userBean.setPassword(user.getPassword());
            userBean.setCreateDate(user.getCreateDate());
            userBean.setModifyId(user.getModifyId());
            userBean.setModifyDate(user.getModifyDate());
            return userBean;
        } catch (DataIntegrityViolationException e) {
            throw new RepeatPersistException("該帳號已被新增過：" + userBean.getAccount());
        }
    }
}
