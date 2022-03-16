package com.jd.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ResultVO<T> implements Serializable {
    private final static Long serialVersionUid = 1L;
    /**
     * 返回状态
     */
    private boolean status;
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;
    /**
     * 数据
     */
    private T data;

    public static <E> ResultVO<E> buildSuccessResult(E data) {

        ResultVO<E> resultVO = new ResultVO<>();
        resultVO.setData(data);
        resultVO.setStatus(true);
        resultVO.setCode(200);
        return resultVO;
    }

    public static <E> ResultVO<E> buildFailResult(String msg) {
        ResultVO<E> resultVO = new ResultVO<>();
        resultVO.setDesc(msg);
        resultVO.setStatus(false);
        resultVO.setCode(500);
        return resultVO;
    }
}
