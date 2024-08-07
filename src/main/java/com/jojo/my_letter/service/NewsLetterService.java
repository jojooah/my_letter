package com.jojo.my_letter.service;

import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.mapper.NewsLetterMapper;
import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsLetterService {
    private final NewsLetterMapper newsLetterMapper;
    private final LoginService loginService;

    public void saveNewsLetter(NewsLetter newsLetter){
        newsLetterMapper.insertNewsLetter(newsLetter);
    }

    public List<NewsLetterHeader> selectNewsLetterList(){
        String username=loginService.getCurrentUsername(); //todo. 아이디로 바꾸어서 넣어야 합니다
        List<NewsLetterHeader> list = newsLetterMapper.selectNewsLetterListByAuthorId(username);

        return newsLetterMapper.selectNewsLetterListByAuthorId(username);
    }
}
