package com.jojo.my_letter.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
@Configuration
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String errMsg = "";
        if (e instanceof UsernameNotFoundException) {
            errMsg = e.getMessage();
        } else if (e instanceof BadCredentialsException) {
            errMsg = e.getMessage();
        } else {
            errMsg = "회원가입에 실패했습니다. 관리자에게 문의하세요";
        }

        httpServletRequest.getSession().setAttribute("errorMessage", errMsg);
        httpServletResponse.sendRedirect("/login?error=errMsg");
    }
}