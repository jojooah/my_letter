package com.jojo.my_letter.controller.service;

import com.jojo.my_letter.mapper.AccessLogMapper;
import com.jojo.my_letter.model.entity.AccessLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccessLogService {
    private final AccessLogMapper accessLogMapper;

    public void saveAceessLog(AccessLog accessLog) {
        accessLogMapper.insertAccessLog(accessLog);
    }
}
