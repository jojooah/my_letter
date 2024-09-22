package com.jojo.my_letter.model.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member implements UserDetails {
    private int memberSeq;
    @NotBlank(message = "아이디를 입력하세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min=8, message = "비밀번호는 8글자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 체크를 위해 한 번 더 입력하세요.")
    @Size(min=8, message = "비밀번호는 8글자 이상이어야 합니다.")
    private String passwordCheck;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    //@NotNull(message = "생년월일을 선택하세요.")
    //private Date birthDate;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String username; //닉네임

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    //@NotBlank(message = "성별을 입력하세요.")
    //private String gender;

    @NotBlank(message = "작가/독자 여부를 선택하세요.")
    private String type; //ROLE_USER, ROLE_ADMIN, ROLE_AUTH

    private String status;
    private String grade;
    private String otpSecret;
    private String profileImageUrl;
    private LocalDateTime lastLoginTime;
    private String ipAddress;
    private ActivityType activityType;

    //해당 유저의 권한을 리턴한다
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //작가일때에는 작가권한
        return List.of(new SimpleGrantedAuthority(type));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}