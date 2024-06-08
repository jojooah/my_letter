package com.jojo.my_letter.api;

import com.jojo.my_letter.model.result.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HealthCheckApi {

    // 누가 쓸까요? AWS ELB에서 헬스체크 api 로 활용합니다.
    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public RestResult healthCheck() {

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("status", "UP");
        data.put("version", "0.0.1");
        data.put("timestamp", LocalDateTime.now());
        return new RestResult(data);
    }

}
