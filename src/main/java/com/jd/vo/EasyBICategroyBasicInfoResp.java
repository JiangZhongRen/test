package com.jd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: liliang56
 * @create: 2021/12/1 20:12
 * @description:
 **/
@Data
public class EasyBICategroyBasicInfoResp {

    @JsonProperty("一级类目ID")
    private Long cate1Id;

    @JsonProperty("一级类目")
    private String cate1Name;

    @JsonProperty("二级类目ID")
    private Long cate2Id;

    @JsonProperty("二级类目")
    private String cate2Name;

    @JsonProperty("三级类目ID")
    private Long cate3Id;

    @JsonProperty("三级类目")
    private String cate3Name;
}
