package com.jojo.my_letter.model.entity;

import com.jojo.my_letter.model.CommonDTO;
import lombok.Data;

import java.util.Date;

@Data
public class Scrap extends CommonDTO {
    private Integer newsLetterHeaderSeq;
    private Integer newsLetterSeq;
    private String userId;
    private ScrapType scrapType; //스크랩 or 구독
    private Date regDate;
}

