package com.jojo.my_letter.service;

import com.jojo.my_letter.config.properties.FileProperties;
import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.exception.MyLetterRuntimeException;
import com.jojo.my_letter.mapper.CategoryCombiMapper;
import com.jojo.my_letter.mapper.NewsLetterMapper;
import com.jojo.my_letter.model.entity.*;
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
    public void saveScrapOrSubscription(Scrap scrap) {
        scrap.setUserId(loginService.getCurrentUserId());
        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        if(scrap.getScrapType().equals(ScrapType.SCRAP) && scrap.getNewsLetterSeq() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SEQ);
        if(scrap.getScrapType().equals(ScrapType.SUBSCRIBE) && scrap.getNewsLetterHeaderSeq() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SEQ);
        if(scrap.getScrapType().equals(ScrapType.SCRAP) && newsLetterMapper.countScrap(scrap)>0) throw new MyLetterRuntimeException(RestErrorCode.ALREADY_SCRAP);
        if(scrap.getScrapType().equals(ScrapType.SUBSCRIBE) && newsLetterMapper.countScrap(scrap)>0) throw new MyLetterRuntimeException(RestErrorCode.ALREADY_SUB);

        newsLetterMapper.insertScrapOrSubscription(scrap);
    }

    /**
     * 구독목록 조회
     * @return
     */
    public List<NewsLetterHeader> selectSubscription(Scrap scrap,int page,int size){
        int offset = (page-1) * size;
        int limit = size;

        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        scrap.setUserId(loginService.getCurrentUserId());

        List<NewsLetterHeader> newsLetterHeaderList = newsLetterMapper.selectSubscription(scrap,limit,offset);
        for(NewsLetterHeader newsLetterHeader : newsLetterHeaderList){
            if(!ObjectUtils.isEmpty(newsLetterHeader.getImagePath())) newsLetterHeader.getImagePath().setFileProperties(fileProperties);
        }
       return newsLetterHeaderList;
    }

    /**
     * 스크랩 목록 조회
     * @return
     */
    public List<NewsLetter> selectScrap(Scrap scrap,int page,int size){
        int offset = (page-1) * size;
        int limit = size;

        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        scrap.setUserId(loginService.getCurrentUserId());

        return newsLetterMapper.selectScrap(scrap,limit,offset);
    }

    /**
     * 스크랩 취소
     * @param scrap
     */
    public void cancelScrapOrDescription(Scrap scrap) {
        scrap.setUserId(loginService.getCurrentUserId());
        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        if(scrap.getScrapType().equals(ScrapType.SUBSCRIBE) && scrap.getNewsLetterHeaderSeq() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SEQ);
        if(scrap.getScrapType().equals(ScrapType.SCRAP) && newsLetterMapper.countScrap(scrap)==0) throw new MyLetterRuntimeException(RestErrorCode.ALREADY_DEL);
        if(scrap.getScrapType().equals(ScrapType.SUBSCRIBE) && newsLetterMapper.countScrap(scrap)==0) throw new MyLetterRuntimeException(RestErrorCode.ALREADY_DEL);

        newsLetterMapper.cancelScrapOrSubscription(scrap);
    }

    /**
     * 스크랩 조회(카운트)
     * @param scrap
     * @return
     */
    public int countScrap(Scrap scrap){
        scrap.setUserId(loginService.getCurrentUserId());
        if(scrap.getScrapType() == null) throw new MyLetterRuntimeException(RestErrorCode.NOT_EXIST_SCRAP_TYPE);
        return newsLetterMapper.countScrap(scrap);
    }

}
