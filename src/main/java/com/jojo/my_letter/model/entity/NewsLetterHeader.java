package com.jojo.my_letter.model.entity;

import com.jojo.my_letter.model.CommonDTO;
import lombok.Data;


@Data
public class NewsLetterHeader extends CommonDTO {
    private Integer newsLetterHeaderSeq;
    private Integer categoryCombiSeq;
    private String headerTitle;             //제목
    private int subscribers;                //구독자 수
    private String authorId;                //작가 아이디
    private String authorName;
    private String description;

    private String cat1Code;
    private String cat2Code;
    private String cat3Code;
    private String cat1Name;
    private String cat2Name;
    private String cat3Name;
    private YN subYn;
    private WeekDay weekDay;
    private ImagePath imagePath;
}
