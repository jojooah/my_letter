package com.jojo.my_letter.model.entity;

import com.jojo.my_letter.config.properties.FileProperties;
import com.jojo.my_letter.model.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ImagePath extends CommonDTO {
    private int imagePathsSeq;
    private int newsLetterSeq;
    private int newsLetterHeaderSeq;
    private String imagePath;
    private String imageUrl;
    private String originName;
    private String savedName;
    private FileProperties fileProperties;  // 외부에서 주입

    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    public String getImageUrl() {
        if (fileProperties == null) {
            throw new IllegalStateException("fileProperties is not set");
        }
        return fileProperties.getWebDir() + savedName;
    }
}