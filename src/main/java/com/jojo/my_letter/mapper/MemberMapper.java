package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member findMember(String name);

    void updatePassword(String name, String encodedPassword);
}