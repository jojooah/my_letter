package com.jojo.my_letter.api;

import com.jojo.my_letter.model.result.RestResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

//fixme
// API 는 @RestController 를 사용합니다.
// 화면 관련된 부분은 @Controller 를 사용하게 됩니다. 단 이경우에 API 를 활용하려면 @ResponseBody 를 사용해야 합니다.
// @RestController 와 @Controller 의 차이점을 알아야 합니다.

//todo
// 백엔드 코드를 더 올려주시면 살펴보겠습니다 :)
// 제 생각에는 우선 만약에 코드 내에서 예외가 발생하는 경우를 어떻게 처리할 수 있는지를 고민해보셔서 올려주시면 좋을 것 같습니다.
@RestController
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
}
