package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.AccessLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccessLogMapper {

    /**
     * 카테고리 조회
     * @return
     */
    void insertAccessLog(@Param("param") AccessLog accessLog);

}