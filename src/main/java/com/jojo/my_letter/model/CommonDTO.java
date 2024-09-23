package com.jojo.my_letter.model;

import lombok.Data;

import java.util.Date;

@Data
public class CommonDTO {
    private String regId;       //작성자id
    private String updId;       //수정자id
    private Date regDate;       //작성일
    private Date updDate;       //수정일
    private String dbStatus;
}
