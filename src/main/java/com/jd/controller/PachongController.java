package com.jd.controller;

import com.jd.service.PaChongService;
import com.jd.service.entity.PaChongEntity;
import com.jd.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @className: PachongController
 * @description: TODO 类描述
 * @author: ext.jiangzhongren
 * @date: 2022/1/26
 **/
@RestController
@RequestMapping("pachong")
@Slf4j
public class PachongController {

    @Autowired
    private PaChongService paChongService;

    @PostMapping("/add")
    public ResultVO<Void> addPachongTask(@RequestBody PaChongEntity paChongEntity) {
        try {
            return paChongService.addPachongTask(paChongEntity);
        } catch (Exception e) {
            log.error("添加PaChong任务失败", e);
            return ResultVO.buildFailResult(e + "");
        }
    }
}
