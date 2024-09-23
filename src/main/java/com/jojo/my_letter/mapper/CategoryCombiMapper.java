package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.CategoryCombi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryCombiMapper {


    /**
     * 카테고리 seq 조회
     *
     */
    Integer selectCategoryCombiSeq(@Param("param") CategoryCombi categoryCombi);


    /**
     * 카테고리 저장
     * @param categoryCombi
     */
    void insertCategory(@Param("param") CategoryCombi categoryCombi);
}