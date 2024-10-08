package com.jojo.my_letter.controller.service;

import com.jojo.my_letter.mapper.AccessLogMapper;
import com.jojo.my_letter.model.entity.AccessLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.jojo.my_letter.config.AsyncConfiguration.ACCESS_LOG_EXECUTOR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessLogService {
    private final AccessLogMapper accessLogMapper;

    // [step 3]
    @Async(ACCESS_LOG_EXECUTOR)
    public void saveAccessLog(AccessLog accessLog) {
        try {
            log.info("Service accessLog: {}", accessLog);
            accessLogMapper.insertAccessLog(accessLog);

        }catch (Exception e) {
            // todo telegram send
        }
    }
}
