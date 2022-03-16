package com.jd.controller;

import com.google.common.collect.Lists;
import com.jd.controller.param.TaskParam;
import com.jd.service.TaskService;
import com.jd.service.entity.TaskEntity;
import com.jd.utils.DateUtils;
import com.jd.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: AddressController
 * @description: TODO 地址controller
 * @author: ext.jiangzhongren
 * @date: 2021/7/21
 **/
@RequestMapping("/task")
@RestController
@Slf4j
public class TaskController {


    @Autowired
    private TaskService taskService;

    @PostMapping("/category")
    public Response category(@RequestBody EasyBiSpectacularsParamQuery easyBiSpectacularsParamQuery) {
        List<EasyBICategroyBasicInfoResp> easyBICategroyBasicInfoResps = Lists.newArrayList(new EasyBICategroyBasicInfoResp());
        for (int i = 0; i < 5; i++) {
            EasyBICategroyBasicInfoResp easyBICategroyBasicInfoResp = new EasyBICategroyBasicInfoResp();
            easyBICategroyBasicInfoResp.setCate1Name("cat" + i);
            easyBICategroyBasicInfoResps.add(easyBICategroyBasicInfoResp);
        }
        return Response.from(200, easyBICategroyBasicInfoResps);
    }

    @PostMapping("/get")
    public PageResultVO<TaskEntity> getData(@RequestBody TaskParam taskParam) {
        log.info("查询任务：{}", taskParam);
        return taskService.getData(taskParam);
    }

    @PostMapping("/del/{id}")
    public ResultVO<Void> delete(@PathVariable("id") Long id) {
        log.info("task删除id：{}", id);
        return taskService.delete(id);
    }

    @PostMapping("/ups/{id}")
    public ResultVO<Void> updateStatus(@PathVariable("id") Long id) {
        log.info("修改状态参数：{}", id);
        return taskService.updateStatus(id);
    }

    @PostMapping("/add")
    public ResultVO<Void> addTask(@RequestBody TaskEntity taskEntity) {
        log.info("添加任务参数：{}", taskEntity);
        return taskService.addTask(taskEntity);
    }

}
