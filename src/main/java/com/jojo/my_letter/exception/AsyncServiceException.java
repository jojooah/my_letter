package com.jojo.my_letter.exception;

import com.jojo.my_letter.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class AsyncServiceException implements RejectedExecutionHandler {
    
    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
    
        log.error("Async Exception is occurred ! ");
        log.error("runnable = {}", Utils.toJson(runnable));
        log.error("executor = {}", Utils.toJson(executor));
    }
}