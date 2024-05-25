package com.jojo.my_letter.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestError {

    private String code;    // 에러 코드
    private String message;

}
