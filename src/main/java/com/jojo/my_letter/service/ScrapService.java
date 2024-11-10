package com.jojo.my_letter.service;

import com.jojo.my_letter.config.properties.FileProperties;
import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.exception.MyLetterRuntimeException;
import com.jojo.my_letter.mapper.CategoryCombiMapper;
import com.jojo.my_letter.mapper.NewsLetterMapper;
import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import com.jojo.my_letter.model.entity.Scrap;
import com.jojo.my_letter.model.result.RestErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final NewsLetterMapper newsLetterMapper;
    private final CategoryCombiMapper categoryCombiMapper;
    private final LoginService loginService;
    private final FileProperties fileProperties;

    /**
     * 스크랩 or 구독하기(type컬럼으로 구분)
     * @param scrap
     */
    public void saveScrapOrDescription(Scrap scrap) {
        scrap.setUserId(loginService.getCurrentUserId());
        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        if(newsLetterMapper.countScrap(scrap)>0) throw new MyLetterRuntimeException(RestErrorCode.ALREADY_SCRAP);

        newsLetterMapper.insertScrapOrSubscription(scrap);
    }

    /**
     * 구독목록 조회
     * @return
     */
    public List<NewsLetterHeader> selectSubscription(Scrap scrap){
        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        scrap.setUserId(loginService.getCurrentUserId());

        List<NewsLetterHeader> newsLetterHeaderList = newsLetterMapper.selectSubscription(scrap);
        for(NewsLetterHeader newsLetterHeader : newsLetterHeaderList){
            if(!ObjectUtils.isEmpty(newsLetterHeader.getImagePath())) newsLetterHeader.getImagePath().setFileProperties(fileProperties);
        }
       return newsLetterHeaderList;
    }

    /**
     * 스크랩 목록 조회
     * @return
     */
    public List<NewsLetter> selectScrap(Scrap scrap){
        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        scrap.setUserId(loginService.getCurrentUserId());

        return newsLetterMapper.selectScrap(scrap);
    }

    /**
     * 스크랩 취소
     * @param scrap
     */
    public void cancelScrapOrDescription(Scrap scrap) {
        scrap.setUserId(loginService.getCurrentUserId());
        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        if(newsLetterMapper.countScrap(scrap)==0) throw new MyLetterRuntimeException(RestErrorCode.ALREADY_DEL);

        newsLetterMapper.cancelScrapOrSubscription(scrap);
    }

}
