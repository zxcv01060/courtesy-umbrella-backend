package tw.edu.ntub.imd.courtesyumbrella.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.courtesyumbrella.bean.UserBean;
import tw.edu.ntub.imd.courtesyumbrella.exception.RequiredParameterException;
import tw.edu.ntub.imd.courtesyumbrella.service.UserService;
import tw.edu.ntub.imd.courtesyumbrella.utils.http.ResponseEntityBuilder;

@Tag(name = "User", description = "使用者")
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            tags = "User",
            method = "POST",
            summary = "註冊",
            description = "註冊一個新的使用者，預設權限為一般使用者",
            responses = @ApiResponse(responseCode = "200", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    )
    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody UserBean userBean) {
        userService.create(userBean);
        return ResponseEntityBuilder.success().message("註冊成功").build();
    }

    public static class UserBeanRequiredParameterException extends RequiredParameterException {
        public UserBeanRequiredParameterException(String requiredParameterName) {
            super("user." + requiredParameterName);
        }
    }
}
