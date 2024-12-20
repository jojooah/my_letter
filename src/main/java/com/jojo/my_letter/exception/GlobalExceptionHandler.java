package com.jojo.my_letter.exception;

import com.jojo.my_letter.model.result.RestError;
import com.jojo.my_letter.model.result.RestErrorCode;
import com.jojo.my_letter.model.result.RestErrorException;
import com.jojo.my_letter.service.thirdparty.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 스프링시큐리티에서 처리해야함: 401(인증에러), 403(인가에러)
 * 400(Bad Request) : RestErrorException
 * 500(Internal Server Error)
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler  {

    private final TelegramService telegramService;

    // 클라이언트 오류.
    @ExceptionHandler({MyLetterRuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected RestError handleMyLetterRuntimeException(MyLetterRuntimeException e) {
        log.error(e.getMessage());
        return new RestError(e.getMessage(), e.getMessage());
    }

    // 클라이언트 오류.
    @ExceptionHandler({RestErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected RestError handleResultCodeException(RestErrorException re) {
        log.error(re.getRestError().getMessage());
        return new RestError(re.getRestError().getCode(), re.getMessage());
    }

    // 서버 오류.
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected RestError handleException(Exception ex) {
        //텔레그램으로 메시지 보내기
        log.error(ex.getMessage());
      //  telegramService.sendTelegram("ServerError! " + ex.getMessage());
        return new RestError(RestErrorCode.ERROR_ETC.getCode(), ex.getMessage());
    }


}
