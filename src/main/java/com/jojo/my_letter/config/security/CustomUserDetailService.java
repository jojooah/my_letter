package com.jojo.my_letter.config.security;

import com.jojo.my_letter.mapper.MemberMapper;
import com.jojo.my_letter.model.entity.Member;
import com.jojo.my_letter.service.thirdparty.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
@RequiredArgsConstructor
//login요청이 오면 자동으로 UserDetailService타입으로 IoC되어있는 loadUserByUsername함수가 실행
public class CustomUserDetailService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private final TelegramService telegramService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        log.info("CustomUserDetailService !! username: {}", id);

        Member member = memberMapper.findMember(id);

        telegramService.sendTelegram("로그인 완료 : " + id);

        if(member == null) {
            log.error("존재하지 않는 유저입니다 ! {}", id);
            throw new UsernameNotFoundException("존재하지 않는 유저 입니다 ! " + id);
        }

        return member;
    }

}