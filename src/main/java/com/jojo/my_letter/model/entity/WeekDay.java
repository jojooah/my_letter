package com.jojo.my_letter.model.entity;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum WeekDay {
    MON("MON", "월요일"),
    TUE("TUE", "화요일"),
    WED("WED", "수요일"),
    THU("THU", "목요일"),
    FRI("FRI", "금요일"),
    SAT("SAT", "토요일"),
    SUN("SUN", "일요일");

    private String code;
    private String name;

    WeekDay(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static WeekDay fromDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(WeekDay.values())
                .filter(weekDay -> weekDay.getCode().equals(dayOfWeek.name().substring(0,3)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid DayOfWeek: " + dayOfWeek));
    }
}