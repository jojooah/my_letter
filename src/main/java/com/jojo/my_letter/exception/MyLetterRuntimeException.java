package com.jojo.my_letter.exception;

import lombok.Data;

@Data
public class MyLetterRuntimeException extends RuntimeException {

    private String code;

    public MyLetterRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }
}
