package com.jojo.my_letter.model.entity;
public enum ActivityType {
    LOG_IN("LOG_IN"),
    LOG_OUT("LOG_OUT");

    private final String activity;

    ActivityType(String activity) {
        this.activity = activity;
    }

}
