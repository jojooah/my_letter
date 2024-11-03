package com.jojo.my_letter.service;

import com.jojo.my_letter.config.properties.FileProperties;
import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.mapper.CategoryCombiMapper;
import com.jojo.my_letter.mapper.NewsLetterMapper;
import com.jojo.my_letter.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
        List<NewsLetterHeader> newsLetterHeaderList = newsLetterMapper.selectNewsLetterHeaderListByAuthorId(userId);
        for(NewsLetterHeader newsLetterHeader : newsLetterHeaderList){
            if(!ObjectUtils.isEmpty(newsLetterHeader.getImagePath())) newsLetterHeader.getImagePath().setFileProperties(fileProperties);
        }
       return newsLetterHeaderList;
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

        if(!ObjectUtils.isEmpty(newsLetterHeader.getImagePath())){
            ImagePath imagePath= newsLetterHeader.getImagePath();
            imagePath.setNewsLetterHeaderSeq(newsLetterHeader.getNewsLetterHeaderSeq());

            saveImg(imagePath);
        }
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

    /**
     * 이미지 저장
     * @param imagePath
     */
    public void saveImg(ImagePath imagePath){
        newsLetterMapper.insertImagePath(imagePath);
    }

    /**
     * 요일별 뉴스레터 조회
     * @param weekDay
     * @return
     */
    public List<NewsLetterHeader> selectNewsLetterHeaderListByWeekDay(WeekDay weekDay,int page,int size){
        int offset = (page-1) * size;
        int limit = size;

        List<NewsLetterHeader> list = newsLetterMapper.selectNewsLetterHeaderListByWeekDay(weekDay,limit,offset);
        if(ObjectUtils.isEmpty(list)) {
            return null;
        }

        for(NewsLetterHeader newsLetterHeader : list){
           if(!ObjectUtils.isEmpty(newsLetterHeader.getImagePath()))
               newsLetterHeader.getImagePath().setFileProperties(fileProperties);
        }
        return list;
    }

    /**
     * 요일별 뉴스레터 조회(카운트)
     * @param weekDay
     * @return
     */
    public int countNewsLetterHeaderListByWeekDay(WeekDay weekDay){
        return newsLetterMapper.countNewsLetterHeaderListByWeekDay(weekDay);
    }

    /**
     * 카테고리별 뉴스레터 조회
     * @param category
     * @return
     */
    public List<NewsLetterHeader> selectNewsLetterHeaderListByCategory(String category,int page,int size){
        int offset = (page-1) * size;
        int limit = size;

        CategoryCombi cc = new CategoryCombi();
        cc.setCat1Code(category);
        List<NewsLetterHeader> list = newsLetterMapper.selectNewsLetterHeaderListByCategory(cc,limit, offset);
        if (ObjectUtils.isEmpty(list)) return new ArrayList<>();

        for(NewsLetterHeader newsLetterHeader : list){
            if(!ObjectUtils.isEmpty(newsLetterHeader.getImagePath()))
                newsLetterHeader.getImagePath().setFileProperties(fileProperties);
        }
        return list;
    }

    /**
     * 카테고리별 뉴스레터 조회(카운트)
     * @param cat1Code
     * @return
     */
    public int countNewsLetterByCategory(String cat1Code){
        CategoryCombi cc = new CategoryCombi();
        cc.setCat1Code(cat1Code);
        return newsLetterMapper.countNewsLetterHeaderListByCategory(cc);
    }


}
