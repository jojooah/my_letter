package com.jojo.my_letter.model.entity;

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
}