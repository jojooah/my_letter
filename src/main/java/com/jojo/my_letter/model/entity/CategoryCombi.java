package com.jojo.my_letter.model.entity;

import com.jojo.my_letter.model.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCombi extends CommonDTO {
    private Integer categoryCombiSeq;        //시퀀스
    private String cat1Code;
    private String cat2Code;
    private String cat3Code;
}
