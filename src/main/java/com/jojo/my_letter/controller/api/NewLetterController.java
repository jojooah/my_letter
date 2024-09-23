package com.jojo.my_letter.controller.api;

import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import com.jojo.my_letter.model.result.RestResult;
import com.jojo.my_letter.service.NewsLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NewLetterController {
    private final NewsLetterService newsLetterService;
    private final WebApplicationContext context;

    @PostMapping("/saveNewsLetter")
    public @ResponseBody RestResult write(@RequestBody NewsLetter newsLetter) {
        Map<String, Object> data = new LinkedHashMap<>();

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

    @PostMapping("/saveNewsLetterHeader")
    public @ResponseBody RestResult saveNewsLetterHeader(@RequestBody NewsLetterHeader newsLetterHeader) {
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            newsLetterService.saveNewsLetterHeader(newsLetterHeader);
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

    @PostMapping(value = "/newsletter/image-upload")
    public @ResponseBody RestResult imageUpload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            // 서버에 저장할 경로
           String uploadDirectory = "C:\\newsletterProject_images\\upload";
            // 업로드 된 파일의 이름
            String originalFileName = file.getOriginalFilename();

            // 업로드 된 파일의 확장자
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 업로드 될 파일의 이름 재설정 (중복 방지를 위해 UUID 사용)
            String uuidFileName = UUID.randomUUID().toString() + fileExtension;

            // 위에서 설정한 서버 경로에 이미지 저장
            file.transferTo(new File(uploadDirectory, uuidFileName));

            System.out.println("************************ 업로드 컨트롤러 실행 ************************");
            System.out.println(uploadDirectory);

            data.put("data",uuidFileName);
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