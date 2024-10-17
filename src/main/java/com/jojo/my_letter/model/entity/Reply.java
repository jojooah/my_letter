package com.jojo.my_letter.model.entity;

import com.jojo.my_letter.model.CommonDTO;
import lombok.Data;


@Data
public class Reply extends CommonDTO {
    private Integer replySeq;
    private Integer newsLetterSeq;
    private String replyContent;                 //내용
    private Integer memberSeq;
    private String username;

    private SaveStatus saveStatus;
    private ImagePath imagePath;
}

