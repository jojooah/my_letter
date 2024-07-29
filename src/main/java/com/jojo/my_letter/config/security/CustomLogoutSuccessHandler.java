package com.jojo.my_letter.config.security;

import com.jojo.my_letter.mapper.MemberMapper;
import com.jojo.my_letter.model.entity.ActivityType;
import com.jojo.my_letter.model.entity.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    private final MemberMapper memberMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {

            String username = authentication.getName();
            System.out.println("User " + username + " has logged out.");
            Member member = (Member)authentication.getPrincipal();

            String ipAddress = getClientIp(request);
            Member findMember = memberMapper.findMember(member.getId());
            findMember.setIpAddress(ipAddress);
            findMember.setActivityType(ActivityType.LOG_OUT);

            //로그아웃 기록
            memberMapper.insertLoginHistory(findMember);
        }

        super.onLogoutSuccess(request, response, authentication);
    }

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

}
