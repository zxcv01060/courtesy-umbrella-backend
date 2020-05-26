package tw.edu.ntub.imd.courtesyumbrella.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tw.edu.ntub.imd.config.Config;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.courtesyumbrella.service.UserService;
import tw.edu.ntub.imd.courtesyumbrella.util.http.ResponseUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @SpringBootApplication(scanBasePackages = "tw.edu.ntub.imd.courtesyumbrella.controller")
    @Import(Config.class)
    static class UserControllerTestApplication {
        @Bean
        public ObjectMapper objectMapper() {
            return ResponseUtils.createMapper();
        }
    }

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("測試正常註冊功能")
    void testRegister() throws Exception {
        UserBean userBean = new UserBean();
        userBean.setAccount("test");
        userBean.setPassword("hello world");
        userBean.setEmail("10646007@ntub.edu.tw");
        userBean.setBirthday(LocalDate.of(2020, 3, 30));
        JSONObject userObject = new JSONObject();
        userObject.put("account", userBean.getAccount());
        userObject.put("password", userBean.getPassword());
        userObject.put("email", userBean.getEmail());
        userObject.put("birthday", userBean.getBirthday()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        String requestBody = userObject.toString();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestBody);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status()
                                   .isOk())
                .andExpect(MockMvcResultMatchers.header()
                                   .string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result")
                                   .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                                   .value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                   .hasJsonPath());
        Mockito.verify(userService, Mockito.times(1))
                .create(userBean);
    }

    @Test
    @DisplayName("測試缺少account欄位的註冊功能")
    void testFailRegister() throws Exception {
        JSONObject userObject = new JSONObject();
        String requestBody = userObject.toString();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status()
                                   .isOk())
                .andExpect(MockMvcResultMatchers.header()
                                   .string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result")
                                   .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                                   .value("Required - user.account"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                   .value("缺少必要參數：user.account"));
    }
}
