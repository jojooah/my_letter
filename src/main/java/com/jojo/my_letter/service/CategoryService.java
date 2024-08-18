package com.jojo.my_letter.service;

import com.jojo.my_letter.mapper.CategoryMapper;
import com.jojo.my_letter.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 조회
     * @return
     */
    public List<Category> selectCategory(){
        return categoryMapper.selectCategory();
    }

}
