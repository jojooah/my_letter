package com.jojo.my_letter.controller.api;

import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.result.RestResult;
import com.jojo.my_letter.service.NewsLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NewLetterController {
    private final NewsLetterService newsLetterService;

    @PostMapping("/write")
    public @ResponseBody RestResult write(@RequestBody NewsLetter newsLetter) {
        Map<String, Object> data = new LinkedHashMap<>();
        log.info(newsLetter.toString());
        try {
            newsLetterService.saveNewsLetter(newsLetter);
            data.put("status", "SUCCESS");
            data.put("message", "저장되었습니다");

            return new RestResult(data);

        } catch (Exception e) {
            data.put("status", "FAIL");
            data.put("message", "오류가 발생했습니다");
            log.error(e.getMessage());

            return new RestResult(data);
        }
    }
}
