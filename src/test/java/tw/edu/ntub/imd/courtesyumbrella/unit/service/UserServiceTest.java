package tw.edu.ntub.imd.courtesyumbrella.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tw.edu.ntub.imd.birc.common.util.bean.BeanUtils;
import tw.edu.ntub.imd.courtesyumbrella.annotation.ServiceTest;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.courtesyumbrella.service.UserService;
import tw.edu.ntub.imd.databaseconfig.dao.UserDAO;
import tw.edu.ntub.imd.databaseconfig.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ServiceTest
@DisplayName("測試UserService")
class UserServiceTest {
    @MockBean
    private UserDAO userDAO;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("測試create方法")
    void testCreate() {
        UserBean userBean = new UserBean();
        userBean.setAccount("test save");
        userBean.setPassword("i am password");
        userBean.setBirthday(LocalDate.of(2020, 4, 5));
        userBean.setEmail("10646007@ntub.edu.tw");
        User returnUser = BeanUtils.copy(userBean, new User());
        returnUser.setPassword("i am encoded password");
        returnUser.setModifyId("test save");
        returnUser.setModifyDate(LocalDateTime.of(
                LocalDate.of(2020, 4, 5),
                LocalTime.of(13, 48, 12)
        ));
        Mockito.when(passwordEncoder.encode(Mockito.anyString()))
                .thenReturn("i am encoded password");
        Mockito.when(userDAO.saveAndFlush(Mockito.any(User.class)))
                .thenReturn(returnUser);
        UserBean createdUserBean = userService.create(userBean);
        Assertions.assertAll(
                () -> Assertions.assertEquals("test save", createdUserBean.getAccount()),
                () -> Assertions.assertNotEquals("i am password", createdUserBean.getPassword()),
                () -> Assertions.assertEquals(LocalDate.of(2020, 4, 5), createdUserBean.getBirthday()),
                () -> Assertions.assertEquals("10646007@ntub.edu.tw", createdUserBean.getEmail()),
                () -> Assertions.assertEquals("test save", createdUserBean.getModifyId()),
                () -> Assertions.assertEquals(
                        LocalDateTime.of(
                                LocalDate.of(2020, 4, 5),
                                LocalTime.of(13, 48, 12)
                        ), createdUserBean.getModifyDate())
        );
    }
}
