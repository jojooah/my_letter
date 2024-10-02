package com.jojo.my_letter.mq.consumer;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConsumerResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String result;
    private String message;
}