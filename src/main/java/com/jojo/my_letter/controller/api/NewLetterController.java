package com.jojo.my_letter.controller.api;

import com.jojo.my_letter.exception.MyLetterRuntimeException;
import com.jojo.my_letter.model.entity.ImagePath;
import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import com.jojo.my_letter.model.result.RestResult;
import com.jojo.my_letter.service.NewsLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NewLetterController {
    private final NewsLetterService newsLetterService;
    private final WebApplicationContext context;

    @Value("${file.upload-dir}")
    String uploadDirectory;

    @PostMapping("/saveNewsLetter")
    public @ResponseBody
    RestResult write(@RequestParam("thumbnail") MultipartFile thumbnail,
                     @RequestPart("newsLetter") NewsLetter newsLetter) {
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            if (!thumbnail.isEmpty()) {
                ImagePath imagePath = (ImagePath) imageUpload(thumbnail).getData().get("data");
                newsLetter.setImagePath(imagePath);
            }

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
    public @ResponseBody
    RestResult saveNewsLetterHeader(@RequestBody NewsLetterHeader newsLetterHeader) {
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

    @PostMapping("/deleteNewsLetter")
    public @ResponseBody
    RestResult deleteNewsLetter(@RequestBody Integer newsLetterSeq) {
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            newsLetterService.deleteNewsLetter(newsLetterSeq);
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
    public @ResponseBody
    RestResult imageUpload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            // 업로드 된 파일의 이름
            String originalFileName = file.getOriginalFilename();

            // 업로드 된 파일의 확장자
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 업로드 될 파일의 이름 재설정 (중복 방지를 위해 UUID 사용)
            String uuidFileName = UUID.randomUUID().toString() + fileExtension;

            // 위에서 설정한 서버 경로에 이미지 저장
            file.transferTo(new File(uploadDirectory, uuidFileName));

            // 웹에서 접근할 수 있는 경로로 변환
            String imageUrl = "/resources/images/upload/" + uuidFileName;

            ImagePath imagePath = new ImagePath();
            imagePath.setOriginName(originalFileName);
            imagePath.setSavedName(uuidFileName);
            imagePath.setImagePath(uploadDirectory + File.separator + uuidFileName);
            imagePath.setImageUrl(imageUrl);

            data.put("data", imagePath);
            data.put("status", "SUCCESS");
            data.put("message", "저장되었습니다");

            return new RestResult(data);

        } catch (Exception e) {
            data.put("status", "FAIL");
            data.put("message", "이미지 저장에 오류가 발생했습니다");
            log.error(e.getMessage());

            return new RestResult(data);
        }

    }

    @PostMapping("/deleteImage")
    @ResponseBody
    public RestResult deleteImage(@RequestParam("imageSrc") String imageSrc) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            int lastSlashIndex = imageSrc.lastIndexOf("/");
            String fileName = imageSrc.substring(lastSlashIndex + 1);// 마지막 슬래시 이후의 문자열 추출

            Path filePath = Paths.get(uploadDirectory, fileName);

            if (Files.deleteIfExists(filePath)) {
                data.put("status", "SUCCESS");
                data.put("message", "삭제되었습니다.");
            } else {
                data.put("status", "FAIL");
                data.put("message", "파일을 찾을 수 없습니다.");
            }
            return new RestResult(data);
        } catch (IOException e) {
            log.error("이미지 삭제에 오류가 발생했습니다 ! {}", e.getMessage(), e);
            throw new MyLetterRuntimeException("FAIL", "이미지 삭제에 오류가 발생했습니다");
        }
    }
}