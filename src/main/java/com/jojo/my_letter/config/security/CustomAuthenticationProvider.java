package com.jojo.my_letter.config.security;

import com.jojo.my_letter.mapper.MemberMapper;
import com.jojo.my_letter.model.entity.ActivityType;
import com.jojo.my_letter.model.entity.Member;
import com.jojo.my_letter.model.entity.UserContext;
import com.jojo.my_letter.service.thirdparty.TelegramService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final TelegramService telegramService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Member member = memberMapper.findMember(id);

        if (member == null) {
            log.error("존재하지 않는 유저입니다 ! {}", id);
            throw new UsernameNotFoundException("존재하지 않는 유저 입니다 ! " + id);
        }

        if (!passwordEncoder.matches(password, member.getPassword())) {
            log.error("잘못된 비밀번호입니다.");
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = getClientIp(request);

        Member findMember = memberMapper.findMember(member.getId());
        findMember.setIpAddress(ipAddress);
        findMember.setActivityType(ActivityType.LOG_IN);

        // 로그인 기록
        memberMapper.insertLoginHistory(findMember);
        telegramService.sendTelegram("로그인 완료 : " + id);
        UserContext.getUserContext().put("memberId", member.getId()); // 쓰레드로컬에 로그인유저 정보 넣기 - 액세스 로그
        return new UsernamePasswordAuthenticationToken(member, password, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
