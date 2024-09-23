package com.jojo.my_letter.model.entity;

import com.jojo.my_letter.model.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends CommonDTO {
    private int categorySeq;        //시퀀스
    private String catCode;         //카테고리 코드
    private String catName;         //카테고리 이름
    private String upperCode;       //상위 카테고리
    private int catLevel;           //카테고리 분류 (대분류,중분류,소분류)
}
