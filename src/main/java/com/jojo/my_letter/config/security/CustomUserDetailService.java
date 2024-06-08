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
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private final TelegramService telegramService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("CustomUserDetailService !! username: {}", username);

        Member member = memberMapper.findMember(username);

        telegramService.sendTelegram("로그인 완료 : " + username);

        if(member == null) {
            log.error("존재하지 않는 유저입니다 ! {}", username);
            throw new UsernameNotFoundException("존재하지 않는 유저 입니다 ! " + username);
        }

        return member;
    }

}