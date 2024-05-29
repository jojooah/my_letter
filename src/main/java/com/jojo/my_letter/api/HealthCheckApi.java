package com.jojo.my_letter.api;

import com.jojo.my_letter.model.result.RestResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
@Controller
public class HealthCheckApi {

    // 누가 쓸까요? AWS ELB에서 헬스체크 api 로 활용합니다.
    @GetMapping("/health")
    public RestResult healthCheck() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("status", "UP");
        data.put("version", "0.0.1");
        data.put("timestamp", LocalDateTime.now());
        return new RestResult(data);
    }

    // 누가 쓸까요? AWS ELB에서 헬스체크 api 로 활용합니다.
    @GetMapping("/index")
    public String healthCheck1() {

        return "index";
    }
    @GetMapping("/newsletter/item")
    public String newsletterItem() {

        return "shop";
    }
    @GetMapping("/newsletter/item/1")
    public String newsletterItem1() {

        return "shop-detail";
    }
}
