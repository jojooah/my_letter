package com.jojo.my_letter.controller.api;

import com.jojo.my_letter.model.entity.ImagePath;
import com.jojo.my_letter.model.entity.NewsLetter;
import com.jojo.my_letter.model.entity.NewsLetterHeader;
import com.jojo.my_letter.model.entity.Reply;
import com.jojo.my_letter.model.result.RestResult;
import com.jojo.my_letter.service.NewsLetterService;
import com.jojo.my_letter.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private final ReplyService replyService;

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

    //todo.1.뉴스레터별로 폴더 만들어서 해당 이미지 저장
    //todo.2.임시 폴더에 저장해두고, 최종적으로 뉴스레터가 저장되면 임시폴더->본폴더로 저장
    //todo. ㄴ 최종적으로 뉴스레터 저장되지 않으면, 임시 폴더에 있는 이미지 삭제로직 추가
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

            File file = new File(uploadDirectory + fileName);

            if (file.exists()) {
                file.delete();
                data.put("status", "SUCCESS");
                data.put("message", "삭제되었습니다.");
                return new RestResult(data);
            } else {
                data.put("status", "FAIL");
                data.put("message", "파일을 찾을 수 없습니다.");
                return new RestResult(data);
            }
        } catch (Exception e) {
            data.put("status", "FAIL");
            data.put("message", "이미지 삭제에 오류가 발생했습니다");
            return new RestResult(data);

        }
    }

    @PostMapping("/saveReply")
    public @ResponseBody
    RestResult saveReply(@RequestBody Reply reply) {
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            replyService.saveReply(reply);
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