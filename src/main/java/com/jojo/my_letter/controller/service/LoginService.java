package com.jojo.my_letter.controller.service;

import com.jojo.my_letter.mapper.MemberMapper;
import com.jojo.my_letter.model.entity.Member;
import com.jojo.my_letter.model.result.RestErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jojo.my_letter.model.result.RestErrorCode.*;
import static io.micrometer.common.util.StringUtils.isBlank;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Member join(Member member) {
        if (isBlank(member.getName())) throw new RestErrorException(UNKNOWN_ERROR);
        if (isBlank(member.getEmail())) throw new RestErrorException(NEED_EMAIL);
        if (isBlank(member.getId())) throw new RestErrorException(NEED_USER_ID);
        if (isBlank(member.getPassword())) throw new RestErrorException(NEED_PWD);
        if (isBlank(member.getPasswordCheck())) throw new RestErrorException(NEED_NEW_PWD_FOR_CHECK);
        if (!member.getPassword().equals(member.getPasswordCheck())) throw new RestErrorException(WRONG_PWD);
        if (isBlank(member.getUsername())) throw new RestErrorException(NEED_NICK_NAME);
        if (member.getPassword().length() < 4) throw new RestErrorException(UNKNOWN_ERROR);
        if (memberMapper.findMember(member.getId()) != null) throw new RestErrorException(USER_ID_ALREADY_EXIST);

        String rawPwd = member.getPassword();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        member.setPassword(encPwd);

        if (memberMapper.insertMember(member) < 1) {
            throw new RestErrorException(UNKNOWN_ERROR);
        }

        return memberMapper.findMember(member.getId());
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername(); //인터페이스이기때문에 저기서 USERNAME에 해당하는걸 가져오면 진짜 username을 가져온다.
            } else {
                return principal.toString();
            }
        }
        return null;
    }

}
