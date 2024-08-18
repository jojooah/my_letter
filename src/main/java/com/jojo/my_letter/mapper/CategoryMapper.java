package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 카테고리 조회
     * @return
     */
    List<Category> selectCategory();

}