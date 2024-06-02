package com.jojo.my_letter.model.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestErrorException extends RuntimeException {
    private RestError restError;

    private Object response;

    public RestErrorException(RestError restError) { this.restError = restError; }

}
