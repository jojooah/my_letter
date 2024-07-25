package com.jojo.my_letter.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("LoginSuccessHandler !! ");
//        log.error(httpServletRequest.getSession().getAttribute("FROM_JOIN_PAGE").toString());
        //회원가입시 바로 로그인 후, 메인페이지로
        if (httpServletRequest.getSession().getAttribute("FROM_JOIN_PAGE") != null) {
            httpServletRequest.getSession().removeAttribute("FROM_JOIN_PAGE");
            getRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, "/");
        } else {
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }
    }

}