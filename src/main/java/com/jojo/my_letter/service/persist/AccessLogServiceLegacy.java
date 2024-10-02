package com.jojo.my_letter.service.persist;

import com.jojo.my_letter.mapper.AccessLogMapper;
import com.jojo.my_letter.model.entity.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.jojo.my_letter.config.AsyncConfiguration.ACCESS_LOG_EXECUTOR;
import static com.jojo.my_letter.utils.Utils.toJson;

@Slf4j
@Service
public class AccessLogServiceLegacy {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Async(ACCESS_LOG_EXECUTOR)
    public void saveAccessLog(AccessLog accessLog) {
        log.info("Service AccessLog ==> {}", toJson(accessLog));
        accessLogMapper.insertAccessLog(accessLog);
    }
}