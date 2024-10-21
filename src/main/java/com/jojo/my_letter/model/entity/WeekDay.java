package com.jojo.my_letter.model.entity;

public enum WeekDay {
    Mon("Mon", "월요일"),
    Tue("Tue", "화요일"),
    Wed("Wed", "수요일"),
    Thu("Thu", "목요일"),
    Fri("Fri", "금요일"),
    Sat("Sat", "토요일"),
    Sun("Sun", "일요일");

    private String code;
    private String name;

    WeekDay(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getter methods for code and name
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}