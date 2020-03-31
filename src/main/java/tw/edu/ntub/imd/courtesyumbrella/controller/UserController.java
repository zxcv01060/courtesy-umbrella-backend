package tw.edu.ntub.imd.courtesyumbrella.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.courtesyumbrella.service.UserService;
import tw.edu.ntub.imd.courtesyumbrella.utils.http.ResponseEntityBuilder;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody UserBean userBean) {
        userService.create(userBean);
        return ResponseEntityBuilder.success().message("註冊成功，將重定向至登入頁面。").build();
    }
}
