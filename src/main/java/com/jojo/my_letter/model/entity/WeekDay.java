package com.jojo.my_letter.model.entity;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum WeekDay {
    MON("MON", "월요일", "월요일, 한 주의 시작!"),
    TUE("TUE", "화요일", "화이팅, 화요일!"),
    WED("WED", "수요일", "수요일은 뭔가 어정쩡한 느낌"),
    THU("THU", "목요일", "목요일은 그냥 왜인지 뉴스레터 보기 좋은 날"),
    FRI("FRI", "금요일", "금요일에 시간 어때요~"),
    SAT("SAT", "토요일", "토요일! 주말 시작!"),
    SUN("SUN", "일요일", "일요일! 아직 하루 남았다구요!");

    private String code;
    private String name;
    private String msg;

    WeekDay(String code, String name, String msg) {
        this.code = code;
        this.name = name;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public static WeekDay fromCode(String code) {
        return Arrays.stream(WeekDay.values())
                .filter(weekDay -> weekDay.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }
}