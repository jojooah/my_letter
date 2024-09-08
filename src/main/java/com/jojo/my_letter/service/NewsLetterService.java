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
        newsLetterMapper.insertNewsLetter(newsLetter);
    }

    public List<NewsLetterHeader> selectNewsLetterList(){
        String userId = loginService.getCurrentUserId();
        return newsLetterMapper.selectNewsLetterListByAuthorId(userId);
    }

    public void saveNewsLetterHeader(NewsLetterHeader newsLetterHeader){
        String userId = loginService.getCurrentUserId();
        newsLetterHeader.setAuthorId(userId);

        CategoryCombi categoryCombi = new CategoryCombi();
        categoryCombi.setCat1Code(newsLetterHeader.getCatCode1());
        categoryCombi.setCat2Code(newsLetterHeader.getCatCode2());
        categoryCombi.setCat3Code(newsLetterHeader.getCatCode3());

        Integer categoryCombiSeq = categoryCombiMapper.selectCategoryCombiSeq(categoryCombi);
        if(ObjectUtils.isEmpty(categoryCombiSeq)) {
            categoryCombiMapper.insertCategory(categoryCombi);
            newsLetterHeader.setCategoryCombiSeq(categoryCombi.getCategoryCombiSeq());
        }

        newsLetterHeader.setCategoryCombiSeq(categoryCombiSeq);
        newsLetterMapper.insertNewsLetterHeader(newsLetterHeader);
    }

}
