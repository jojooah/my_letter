package com.jojo.my_letter.model.result;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RestErrorCode {
    SUCCESS("1000", "성공"),

    //1000번대 유저에러
    NEED_USER_ID("1001", "아이디를 입력하세요."),
    NEED_PWD("1002", "비밀번호를 입력하세요"),
    USER_ID_NOT_EXIST("1003", "가입되지 않은 아이디입니다."),
    WRONG_PWD("1004", "비밀번호가 일치하지 않습니다."),
    PLEASE_LOGIN("1005", "로그인 해주세요."),
    USER_ID_ALREADY_EXIST("1006", "이미 가입된 아이디입니다"),
    NEED_NICK_NAME("1007", "닉네임을 입력하세요."),
    NICK_NAME_ALREADY_EXIST("1008", "이미 존재하는 닉네임입니다"),
    NEED_NEW_PWD("1009", "변경할 비밀번호를 입력하세요."),
    NEED_NEW_PWD_FOR_CHECK("1010", "비밀번호 체크를 위해 한번 더 입력해 주세요."),
    NEED_OLD_PWD("1011", "기존 비밀번호를 입력하세요."),
    NEW_PWD_IS_DIFFERENT_FROM_NEW_PWD_FOR_CHECK("1012", "변경할 비밀번호가 서로 일치하지 않습니다."),
    NOT_CORRECT_OLD_PWD("1013", "기존 비밀번호가 틀렸습니다."),
    ALREADY_USED_PWD("1014", "기존 비밀번호는 사용하실 수 없습니다."),
    PWD_IS_DIFFERENT_FROM_PWD_FOR_CHECK("1015", "비밀번호가 서로 일치하지 않습니다."),
    NOT_EMAIL_AUTH("1016", "이메일 인증을 진행해주세요."),
    NOT_CORRECT_EMAIL_KEY("1017", "올바르지 않은 인증키입니다."),
    NEED_EMAIL("1018", "이메일을 입력하세요"),
    WRONg_URL("1019", "올바르지 않은 url입니다"),
    NO_PATHVARIABLE("1020", "path variable이 없습니다"),
    NOT_WITTER("1021","게시물 작성자가 아닙니다."),
    NOT_EXIST_NICKNAME("1022","닉네임을 입력하세요."),
    NOT_EXIST_NAME("1023","이름을 입력하세요."),

    NOT_EXIST_SCRAP_TYPE("3000","구독 or 스크랩 타입이 없습니다."),
    ALREADY_SCRAP("3001","이미 스크랩한 뉴스레터입니다."),
    ALREADY_DEL("3002","이미 삭제되었습니다."),
    ERROR_ETC("9999", "기타 오류"),

    UNKNOWN_ERROR("1000", "알수 없는 오류");

    private String code;    // 에러 코드
    private String message;

    RestErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
