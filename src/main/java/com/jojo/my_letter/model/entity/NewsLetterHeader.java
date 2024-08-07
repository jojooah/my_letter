package com.jojo.my_letter.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NewsLetterHeader {
    private Integer newsLetterHeaderSeq;
    private Integer categorySeq;
    private String headerTitle;             //제목
    private int subscribers;                //구독자 수
    private String authorId;                //작가 아이디

    private String dbStatus;                //데이터 상태 : OK / DEL
    private Date regDate;                   //등록일
    private Date updDate;                   //수정일
}
