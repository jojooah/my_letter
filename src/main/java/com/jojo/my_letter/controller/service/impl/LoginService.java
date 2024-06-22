package com.jojo.my_letter.controller.service.impl;

import com.jojo.my_letter.mapper.MemberMapper;
import com.jojo.my_letter.model.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean join(Member member) throws Exception {

        if (!member.getPassword().equals(member.getPasswordCheck())) throw new Exception("비밀번호가 일치하지 않습니다.");

        String rawPwd = member.getPassword();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        member.setPassword(encPwd);

        memberMapper.insertMember(member);

        return true;

    }
}
