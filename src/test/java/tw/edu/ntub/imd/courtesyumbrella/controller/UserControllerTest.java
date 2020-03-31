package tw.edu.ntub.imd.courtesyumbrella.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@Sql("classpath:data.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @LocalServerPort
    private int port;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl;

    @BeforeEach
    public void initBaseUrl() {
        this.baseUrl = "http://localhost:" + port + contextPath.substring(0, contextPath.length() - 1);
    }

    @Test
    void testRegister() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserBean userBean = new UserBean();
        userBean.setAccount("test");
        userBean.setPassword("hello world");
        userBean.setEmail("10646007@ntub.edu.tw");
        userBean.setBirthday(LocalDateTime.of(LocalDate.of(1999, 4, 3), LocalTime.MAX));
        HttpEntity<UserBean> requestEntity = new HttpEntity<>(userBean, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/user/register", requestEntity, String.class);
        assertSame(HttpStatus.OK, response.getStatusCode());
        JSONObject bodyResult = new JSONObject(response.getBody());
        assertTrue(bodyResult.getBoolean("result"));
        assertEquals("", bodyResult.getString("errorCode"));
        assertFalse(bodyResult.getString("message").isBlank());
    }
}
