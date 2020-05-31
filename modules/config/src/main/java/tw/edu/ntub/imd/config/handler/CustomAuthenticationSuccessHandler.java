package tw.edu.ntub.imd.config.handler;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.config.utils.JwtUtils;
import tw.edu.ntub.imd.config.utils.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;

    @Autowired
    public CustomAuthenticationSuccessHandler(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        response.setHeader("X-Auth-Token", jwtUtils.getToken(userDetails));
        ResponseUtils.response(
                response,
                200,
                true,
                ResponseUtils.LOGIN_SUCCESS_ERROR_CODE,
                ResponseUtils.LOGIN_SUCCESS_MESSAGE,
                JsonNodeFactory.instance.objectNode()
        );
    }
}
