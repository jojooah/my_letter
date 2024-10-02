package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.AccessLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessLogMapper {

    int insertAccessLog(AccessLog accessLog);
}