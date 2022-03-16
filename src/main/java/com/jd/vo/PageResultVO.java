package com.jd.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResultVO<T> {
    /**
     * 接口调用状态
     */
    private Boolean status;
    /**
     * 错误描述
     */
    private String desc;
    /**
     * 每次查询多少条
     */
    private Integer pageSize;
    /**
     * 每次从第几条查询
     */
    private Integer pageNum;
    /**
     * 一共有多少条
     */
    private Long total;
    /**
     * 查询的数据
     */
    private List<T> data;
}
