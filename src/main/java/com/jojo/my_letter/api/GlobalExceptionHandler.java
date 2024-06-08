package com.jojo.my_letter.api;

import com.jojo.my_letter.model.result.RestError;
import com.jojo.my_letter.model.result.RestErrorCode;
import com.jojo.my_letter.model.result.RestErrorException;
import com.jojo.my_letter.model.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

/**
 * 스프링시큐리티에서 처리해야함: 401(인증에러), 403(인가에러)
 * 400(Bad Request) : RestErrorException
 * 500(Internal Server Error)
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 클라이언트 오류.
    @ExceptionHandler({RestErrorException.class})
    protected ResponseEntity<Object> handleResultCodeException(RestErrorException re) {
        RestResult result = new RestResult();

        result.setRestError(re.getRestError());
        result.setMessage(re.getRestError().getMessage());
        log.error(re.getRestError().getMessage());

        return ResponseEntity.status(re.getRestError().getHttpStatus()).body(result);
    }

    // 서버 오류.
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleException(Exception ex) {
        RestResult result = new RestResult();

        result.setMessage("의도하지 못한 에러");
        result.setRestError(RestErrorCode.ERROR_ETC);
        log.error(ex.getMessage());

        // todo: 텔레그램으로 메시지 보내기.
        return ResponseEntity.status(result.getRestError().getHttpStatus()).body(result);
    }

//    @ExceptionHandler({RestErrorException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected RestError handleResultCodeException2(RestErrorException re) {
//        log.error(re.getRestError().getMessage());
//        return new RestError(re.getRestError().getCode(), re.getMessage());
//    }
//    @ExceptionHandler({SQLException.class})
//    protected ResponseEntity<Object> handleSQLException(RestErrorException re) {
//        RestResult result = new RestResult();
//
//        result.setRestError(re.getRestError());
//        result.setMessage(re.getRestError().getMessage());
//        log.error(re.getRestError().getMessage());
//
//        return ResponseEntity.status(re.getRestError().getHttpStatus()).body(result);
//    }
//
//    @ExceptionHandler({NullPointerException.class})
//    protected ResponseEntity<Object> handleNullPointerException(RestErrorException re) {
//        RestResult result = new RestResult();
//
//        result.setRestError(re.getRestError());
//        result.setMessage(re.getRestError().getMessage());
//        log.error(re.getRestError().getMessage());
//
//        return ResponseEntity.status(re.getRestError().getHttpStatus()).body(result);
//    }

}
