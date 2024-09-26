package com.jojo.my_letter.model.entity;
import lombok.Data;
import java.util.Date;

@Data
public class NewsLetter extends NewsLetterHeader {
    private Integer newsLetterSeq;
    private Integer newsLetterHeaderSeq;
    private String number;                  // 회차
    private String title;                   //제목
    private String content;                 //내용
    private String description;
    private Integer cost;                   //가격
    private String freeYn;                  //무료여부
    private Date regDate;                   //등록일
    private Date updDate;                   //수정일
    private String authorName;              // 작가 이름
    private int view;
}

