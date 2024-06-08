package com.jojo.my_letter.model;


public class User extends CommonDTO {
    Integer userSeq;
    String userId;
    String pwd;
    String email;
    String emailAuthYn;     // 이메일 인증 여부
    String name;
    String sex;
    String birthDate;
    String nickname;
    String kakaoYn;         //카카오로그인여부
    String writerYn;        // 작가 or 일반사용자
    String useYn;           //탈퇴여부
}
