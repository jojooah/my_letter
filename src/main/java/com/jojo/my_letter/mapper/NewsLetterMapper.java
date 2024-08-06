package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.NewsLetter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NewsLetterMapper {

    int insertNewsLetter(@Param("param") NewsLetter newsLetter);
}