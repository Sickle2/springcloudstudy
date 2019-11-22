package com.sickle.servicesecurityoauth2.config;

import com.sickle.servicesecurityoauth2.domain.Response;
import com.sickle.servicesecurityoauth2.domain.ResponseMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: lianhai
 * Date: 2019-05-16 15:25
 */
@Slf4j
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.debug("退出成功");
        response.setContentType("application/json;charset=UTF-8");
        Response data = new Response().success(ResponseMeta.LOGOUT_SUCCESS);
        response.getWriter().write(data.toString());
    }
}
