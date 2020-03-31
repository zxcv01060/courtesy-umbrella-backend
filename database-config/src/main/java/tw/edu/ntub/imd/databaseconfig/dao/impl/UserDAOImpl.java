package tw.edu.ntub.imd.databaseconfig.dao.impl;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.databaseconfig.entity.User;

@Repository
public class UserDAOImpl extends BaseDAOImpl<String, User> implements UserDAO {

}
