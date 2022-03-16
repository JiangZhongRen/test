package com.jd.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SkuJdMapper {
    void bachInsert(List<Map<String,String>> maps);
}
