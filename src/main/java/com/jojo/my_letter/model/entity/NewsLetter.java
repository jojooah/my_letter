package com.jojo.my_letter.model.entity;
import com.jojo.my_letter.model.CommonDTO;
import lombok.Data;
import java.util.Date;

@Data
public class NewsLetter extends CommonDTO {
    private Integer newsLetterSeq;
    private Integer newsLetterHeaderSeq;
    private String title;         //제목
    private String content;                 //내용
    private Integer cost;                   //가격
    private String freeYn;                  //무료여부
    private Date regDate;                   //등록일
    private Date updDate;                   //수정일

}
