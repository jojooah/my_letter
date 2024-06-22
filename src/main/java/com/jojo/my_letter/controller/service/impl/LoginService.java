package com.jojo.my_letter.controller.service.impl;

import com.jojo.my_letter.mapper.MemberMapper;
import com.jojo.my_letter.model.entity.Member;
import com.jojo.my_letter.model.result.RestErrorCode;
import com.jojo.my_letter.model.result.RestErrorException;
import io.micrometer.common.util.StringUtils;
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
        if (StringUtils.isBlank(member.getName())) throw  new Exception("이름을 입력하세요.");
        if (StringUtils.isBlank(member.getEmail())) throw  new RestErrorException(RestErrorCode.NEED_EMAIL);
        if (StringUtils.isBlank(member.getId())) throw new RestErrorException(RestErrorCode.NEED_USER_ID);
        if (StringUtils.isBlank(member.getPassword())) throw new RestErrorException(RestErrorCode.NEED_PWD);
        if (StringUtils.isBlank(member.getPasswordCheck())) throw new RestErrorException(RestErrorCode.NEED_NEW_PWD_FOR_CHECK);
        if (!member.getPassword().equals(member.getPasswordCheck())) throw new  RestErrorException(RestErrorCode.WRONG_PWD);
        if (StringUtils.isBlank(member.getUsername())) throw new RestErrorException(RestErrorCode.NEED_NICK_NAME);
        if (member.getPassword().length() < 4) throw new Exception("비밀번호는 4글자 이상이어야 합니다.");
        if (memberMapper.findMember(member.getId())!=null)  throw new RestErrorException(RestErrorCode.USER_ID_ALREADY_EXIST);

        String rawPwd = member.getPassword();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        member.setPassword(encPwd);

        return memberMapper.insertMember(member) > 0;

    }
}
