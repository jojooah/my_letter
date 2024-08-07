package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsLetterMapper {

    /**
     * 뉴스레터 생성
     * @param newsLetter
     * @return
     */
    int insertNewsLetter(@Param("param") NewsLetter newsLetter);

    /**
     * 뉴스레터 헤더 생성
     * @param authorId
     * @return
     */
    List<NewsLetterHeader> selectNewsLetterListByAuthorId(@Param("param") String authorId);
}