package com.jojo.my_letter.api;

import com.jojo.my_letter.model.result.RestError;
import com.jojo.my_letter.model.result.RestErrorException;
import com.jojo.my_letter.model.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RestErrorException.class})
    protected ResponseEntity<Object> handleResultCodeException(RestErrorException re) {
        RestResult result = new RestResult();

        result.setRestError(re.getRestError());
        result.setMessage(re.getRestError().getMessage());
        log.error(re.getRestError().getMessage());

        return ResponseEntity.status(re.getRestError().getHttpStatus()).body(result);
    }

    @ExceptionHandler({SQLException.class})
    protected ResponseEntity<Object> handleSQLException(RestErrorException re) {
        RestResult result = new RestResult();

        result.setRestError(re.getRestError());
        result.setMessage(re.getRestError().getMessage());
        log.error(re.getRestError().getMessage());

        return ResponseEntity.status(re.getRestError().getHttpStatus()).body(result);
    }

    @ExceptionHandler({NullPointerException.class})
    protected ResponseEntity<Object> handleNullPointerException(RestErrorException re) {
        RestResult result = new RestResult();

        result.setRestError(re.getRestError());
        result.setMessage(re.getRestError().getMessage());
        log.error(re.getRestError().getMessage());

        return ResponseEntity.status(re.getRestError().getHttpStatus()).body(result);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        RestResult result = new RestResult();

        result.setMessage("의도하지 못한 에러");
        result.setRestError(RestError.ERROR_ETC);
        log.error(ex.getMessage());
        return ResponseEntity.status(result.getRestError().getHttpStatus()).body(result);
    }

}
