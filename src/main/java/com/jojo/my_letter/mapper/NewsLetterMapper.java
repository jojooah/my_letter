package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.ImagePath;
import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import com.jojo.my_letter.model.entity.WeekDay;
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
     * 뉴스레터 헤더 리스트 조회
     * @param authorId
     * @return
     */
    List<NewsLetterHeader> selectNewsLetterListByAuthorId(@Param("param") String authorId);

    /**
     * 뉴스레터 헤더 조회
     * @param newsLetterHeaderSeq
     * @return
     */
    NewsLetterHeader selectNewsLetterHeaderBySeq(@Param("param") Integer newsLetterHeaderSeq);

    /**
     * 뉴스레터 헤더 생성
     * @param newsLetterHeader
     */
    void insertNewsLetterHeader(@Param("param") NewsLetterHeader newsLetterHeader);

    /**
     * 뉴스레터리스트 조회
     * @param newsLetterHeaderSeq
     * @return
     */
    List<NewsLetter> selectNewsLetterListByHeaderSeq(@Param("param") Integer newsLetterHeaderSeq);

    /**
     * 뉴스레터 조회
     * @param newsLetterSeq
     * @return
     */
    NewsLetter selectNewsLetterBySeq(@Param("param") Integer newsLetterSeq);

    /**
     * 뉴스레터 삭제
     * @param newsLetterSeq
     */
    void deleteNewsLetterBySeq(@Param("param") Integer newsLetterSeq);

    /**
     * 뉴스레터 수정
     * @param newsLetter
     */
    void updateNewsLetter(@Param("param") NewsLetter newsLetter);

    /**
     * 이미지 저장
     * @param imagePath
     */
    void insertImagePath(@Param("param") ImagePath imagePath);

    /**
     * 요일별 뉴스레터 조회
     * @param weekDay
     * @return
     */
    List<NewsLetter> selectNewsLetterListByWeekDay(@Param("param") WeekDay weekDay);
}