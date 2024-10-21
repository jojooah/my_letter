package com.jojo.my_letter.service;

import com.jojo.my_letter.config.properties.FileProperties;
import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.mapper.CategoryCombiMapper;
import com.jojo.my_letter.mapper.NewsLetterMapper;
import com.jojo.my_letter.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsLetterService {
    private final NewsLetterMapper newsLetterMapper;
    private final CategoryCombiMapper categoryCombiMapper;
    private final LoginService loginService;
    private final FileProperties fileProperties;

    public void saveNewsLetter(NewsLetter newsLetter){
        if(ObjectUtils.isEmpty(newsLetter.getNewsLetterSeq())){
            newsLetterMapper.insertNewsLetter(newsLetter);

            if(!ObjectUtils.isEmpty(newsLetter.getImagePath())){
                ImagePath imagePath= newsLetter.getImagePath();
                imagePath.setNewsLetterSeq(newsLetter.getNewsLetterSeq());

                saveImg(imagePath);
            }
        }
       else{
           newsLetterMapper.updateNewsLetter(newsLetter);
        }
    }

    /**
     * 뉴스레터 헤더 리스트 조회
     * @return
     */
    public List<NewsLetterHeader> selectNewsLetterHeaderList(){
        String userId = loginService.getCurrentUserId();
        return newsLetterMapper.selectNewsLetterListByAuthorId(userId);
    }

    /**
     * 뉴스레터 헤더 조회
     * @param seq
     * @return
     */
    public NewsLetterHeader selectNewsLetterHeader(Integer seq){
        return newsLetterMapper.selectNewsLetterHeaderBySeq(seq);
    }

    /**
     * 뉴스레터 헤더 저장
     * @param newsLetterHeader
     */
    public void saveNewsLetterHeader(NewsLetterHeader newsLetterHeader){
        String userId = loginService.getCurrentUserId();
        newsLetterHeader.setAuthorId(userId);

        CategoryCombi categoryCombi = new CategoryCombi();
        categoryCombi.setCat1Code(newsLetterHeader.getCat1Code());
        categoryCombi.setCat2Code(newsLetterHeader.getCat2Code());
        categoryCombi.setCat3Code(newsLetterHeader.getCat3Code());

        Integer categoryCombiSeq = categoryCombiMapper.selectCategoryCombiSeq(categoryCombi);
        if(ObjectUtils.isEmpty(categoryCombiSeq)) {
            categoryCombiMapper.insertCategory(categoryCombi);
            categoryCombiSeq=categoryCombi.getCategoryCombiSeq();

        }

        newsLetterHeader.setCategoryCombiSeq(categoryCombiSeq);
        newsLetterMapper.insertNewsLetterHeader(newsLetterHeader);
    }

    /**
     * 뉴스레터 리스트 조회
     * @return
     */
    public List<NewsLetter> selectNewsLetterList(Integer newsletterHeaderSeq){
        return newsLetterMapper.selectNewsLetterListByHeaderSeq(newsletterHeaderSeq);
    }

    /**
     * 뉴스레터 조회
     * @param newsletterSeq
     * @return
     */
    public NewsLetter selectNewsLetter(Integer newsletterSeq) {
        NewsLetter newsLetter = newsLetterMapper.selectNewsLetterBySeq(newsletterSeq);
        if(newsLetter.getImagePath() != null){
            newsLetter.getImagePath().setFileProperties(fileProperties);
        }
        return newsLetter;
    }

    /**
     * 뉴스레터 삭제
     * @param newsLetterSeq
     */
    public void deleteNewsLetter(Integer newsLetterSeq){
        newsLetterMapper.deleteNewsLetterBySeq(newsLetterSeq);
    }

    public void saveImg(ImagePath imagePath){
        newsLetterMapper.insertImagePath(imagePath);
    }

    public List<NewsLetter> selectNewsLetterListByWeekDay(WeekDay weekDay){
        return newsLetterMapper.selectNewsLetterListByWeekDay(weekDay);
    }
}
