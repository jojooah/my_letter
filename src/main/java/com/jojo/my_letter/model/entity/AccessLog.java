package com.jojo.my_letter.model.entity;

import com.jojo.my_letter.model.CommonDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class AccessLog extends CommonDTO {
    private Long seq;
    private String memberId;
    private String email;
    private String threadId;
    private String host;
    private String authorization;
    private String method;
    private String uri;
    private String service;
    private String os;
    private String deviceClass;
    private String agentName;
    private String agentClass;
    private String clientIp;
    private String country;
    private String city;
    private long elapsed;
    private String request;
    private String response;
    private String status;
    private String deviceName;
    private String osName;
    private String osVersion;
    private String userAgent;
    private String referer;
    private String errorId;
    private LocalDateTime requestAt;
    private LocalDateTime responseAt;
    private String requestId;
    private int errorStatus;
}