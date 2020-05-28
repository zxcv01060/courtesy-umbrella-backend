package tw.edu.ntub.imd.courtesyumbrella.integration.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tw.edu.ntub.imd.courtesyumbrella.annotation.IntegrationTest;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.InputDataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.supplier.DataSupplier;
import tw.edu.ntub.imd.courtesyumbrella.data.supplier.UserDataSupplier;
import tw.edu.ntub.imd.courtesyumbrella.util.DataParameterUtils;

import java.util.List;

@IntegrationTest
@DisplayName("UserController整合測試")
public class TestUserController {
    @Autowired
    private MockMvc mockMvc;
    private final DataSupplier userDataSupplier = UserDataSupplier.getInstance();

    @Test
    @DisplayName("測試正常註冊功能")
    void testSuccessRegister() throws Exception {
        List<DataParameter> allValidDataList = userDataSupplier.getAllValidDataList();
        String content = DataParameterUtils.transferToJsonString(allValidDataList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(
                        MockMvcResultMatchers.status()
                                .isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.result")
                                .value(true)
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.errorCode")
                                .value("")
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.message")
                                .value("註冊成功")
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data")
                                .hasJsonPath()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data")
                                .isMap()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data")
                                .isEmpty()
                );
    }

    @Test
    @DisplayName("測試重複註冊")
    void testRepeatRegister() throws Exception {
        List<DataParameter> allValidDataList = List.of(
                new InputDataParameter("account", "admin"),
                new InputDataParameter("password", "123"),
                new InputDataParameter("email", "a@ntub.edu.tw"),
                new InputDataParameter("birthday", "2020/05/26")
        );
        String content = DataParameterUtils.transferToJsonString(allValidDataList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(content);
        mockMvc.perform(requestBuilder)
                .andExpect(
                        MockMvcResultMatchers.status()
                                .isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.result")
                                .value(false)
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.errorCode")
                                .value("DAO - RepeatPersist")
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.message")
                                .value("該帳號已被新增過：admin")
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data")
                                .hasJsonPath()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data")
                                .isMap()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data")
                                .isEmpty()
                );
    }
}
