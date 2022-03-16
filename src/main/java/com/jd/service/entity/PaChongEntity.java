package com.jd.service.entity;

import lombok.Data;

import java.util.Date;

/**
 * @className: PaChongEntity
 * @author: ext.jiangzhongren
 * @date: 2022/1/26
 **/
@Data
public class PaChongEntity {
    /**
     * id
     */
    private Long id;
    /**
     * url
     */
    private String url;
    /**
     * 参数
     */
    private String param;
    /**
     * 描述
     */
    private String desc;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
