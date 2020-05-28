package tw.edu.ntub.imd.courtesyumbrella.unit.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tw.edu.ntub.imd.courtesyumbrella.TestApplication;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.courtesyumbrella.controller.UserController;
import tw.edu.ntub.imd.courtesyumbrella.data.supplier.UserDataSupplier;
import tw.edu.ntub.imd.courtesyumbrella.service.UserService;
import tw.edu.ntub.imd.courtesyumbrella.util.request.TestCreateOrUpdateRequest;
import tw.edu.ntub.imd.courtesyumbrella.util.request.supplier.PostRequestSupplier;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = TestApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("測試註冊功能")
    void testRegister() throws Exception {
        TestCreateOrUpdateRequest testRequest = new TestCreateOrUpdateRequest(
                mockMvc,
                "/user/register",
                "註冊成功",
                UserDataSupplier.getInstance(),
                new PostRequestSupplier(),
                times -> Mockito.verify(userService, Mockito.times(times))
                        .create(Mockito.any(UserBean.class))
        );
        testRequest.testRequest();
    }
}
