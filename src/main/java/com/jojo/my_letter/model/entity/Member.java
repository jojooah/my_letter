package com.jojo.my_letter.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {
    private int memberSeq;
    private String id;
    private String password;
    private String passwordCheck;
    private String name;
    private Date birthDate;
    private String username; //닉네임
    private String email;
    private String gender;
    private String type; //ROLE_USER, ROLE_ADMIN, ROLE_AUTH
    private String status;
    private String grade;
    private String otpSecret;
    private String profileImageUrl;

    //해당 유저의 권한을 리턴한다
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //작가일때에는 작가권한
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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