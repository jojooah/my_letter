package com.jojo.my_letter.model.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestErrorException extends RuntimeException {
    private RestErrorCode restError;

    private Object response;

    public RestErrorException(RestErrorCode restError) { this.restError = restError; }

}
