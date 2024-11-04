package com.jojo.my_letter.exception;

import com.jojo.my_letter.model.result.RestErrorCode;


public class MyLetterRuntimeException extends RuntimeException {

    private String code;
    private String message;

    public MyLetterRuntimeException(RestErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
