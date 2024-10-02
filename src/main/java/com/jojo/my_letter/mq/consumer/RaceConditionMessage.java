package com.jojo.my_letter.mq.consumer;

import lombok.Data;

import java.io.Serializable;

@Data
public class RaceConditionMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private long seq;
    private String name;
    private String message;
    private String userAgent;
}