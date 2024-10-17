package com.jojo.my_letter.model.entity;
public enum SaveStatus {
    U("UPDATE"),
    D("DELETE"),
    I("INSERT");

    private String code;

    SaveStatus(String code) {
        this.code = code;

    }
}
