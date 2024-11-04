package com.jojo.my_letter.model.entity;

public enum ScrapType {
    SUBSCRIBE("SUBSCRIBE"),
    SCRAP("SCRAP");

    private final String type;
    
    ScrapType(String type){
        this.type = type;
    }

}
