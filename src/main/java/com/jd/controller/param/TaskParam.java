package com.jd.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: TaskParam
 * @description:
 * @author: jiangzhongren
 * @date: 2021/12/10
 **/
@Data
public class TaskParam implements Serializable {
    private final static Long serialVersionUid = 1L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 每页容量
     */
    private Integer pageSize;

    public TaskParam(Long id, Integer currentPage, Integer pageSize) {
        this.id = id;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public TaskParam() {
    }
}
