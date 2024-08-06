package com.jojo.my_letter.model.entity;
import lombok.Data;
import java.util.Date;

@Data
public class NewsLetter {
    private Integer newsLetterSeq;
    private String title;         //제목
    private String content;                 //내용
    private int subscribers;                //구독자 수
    private Integer cost;                   //가격
    private String freeYn;                  //무료여부
    private Date regDate;                   //등록일
    private Date updDate;                   //수정일
    private String authorId;                //작가 아이디
}
