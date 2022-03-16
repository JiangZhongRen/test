package com.jd.vo;


import lombok.Data;

import java.util.List;

@Data
public class EasyBiSpectacularsParamQuery {

    private String pin;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 门店名称
     */
    private List<String> storeNames;
    /**
     * 门店ID
     */
    private List<String> storeIds;
    /**
     * sku_ID
     */
    private List<String> skuIds;
    /**
     * 类目1名称
     */
    private List<String> cate1Names;
    /**
     * 类目2名称
     */
    private List<String> cate2Names;
    /**
     * 类目3名称
     */
    private List<String> cate3Names;

}
