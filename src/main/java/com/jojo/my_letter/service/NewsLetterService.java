package com.jojo.my_letter.service;

import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.mapper.CategoryCombiMapper;
import com.jojo.my_letter.mapper.NewsLetterMapper;
import com.jojo.my_letter.model.entity.CategoryCombi;
import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
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

    public void saveNewsLetter(NewsLetter newsLetter){
        NewsLetter n = newsLetter;
        newsLetterMapper.insertNewsLetter(newsLetter);
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
        return newsLetterMapper.selectNewsLetterBySeq(newsletterSeq);
    }

}
