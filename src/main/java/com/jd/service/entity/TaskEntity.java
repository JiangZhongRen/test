package com.jd.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @className: TaskEntity
 * @description: TODO 类描述
 * @author: ext.jiangzhongren
 * @date: 2021/12/10
 **/
@Data
public class TaskEntity {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    private Date completionTime;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 详细描述
     */
    private String taskDesc;
    /**
     * 预计完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date estimateCompletionTime;
    /**
     * 超时时间
     */
    private String timeout;
    /**
     * 任务状态
     */
    private String taskStatus;
}
