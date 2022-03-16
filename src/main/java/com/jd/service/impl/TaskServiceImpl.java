package com.jd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.controller.param.TaskParam;
import com.jd.mapper.TaskMapper;
import com.jd.service.TaskService;
import com.jd.service.entity.TaskEntity;
import com.jd.vo.PageResultVO;
import com.jd.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @className: AddressServiceImpl
 * @description: TODO 类描述
 * @author: ext.jiangzhongren
 * @date: 2021/7/21
 **/
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public ResultVO<Void> delete(Long id) {
        int i = taskMapper.delete(id);
        if (i == 1) {
            return ResultVO.buildSuccessResult(null);
        }
        log.error("TaskServiceImpl.delete{}", i);
        return ResultVO.buildFailResult("删除失败！");
    }

    @Override
    public PageResultVO<TaskEntity> getData(TaskParam taskParam) {
        PageHelper.startPage(taskParam.getCurrentPage(), taskParam.getPageSize());
        List<TaskEntity> data = taskMapper.getData(taskParam);
        PageInfo<TaskEntity> taskEntityPageInfo = new PageInfo<>(data);
        log.info("taskMapper.getData{}", data);
        PageResultVO<TaskEntity> pageResultVO = new PageResultVO<>();
        pageResultVO.setData(taskEntityPageInfo.getList());
        pageResultVO.setPageNum(taskParam.getCurrentPage());
        pageResultVO.setPageSize(taskParam.getPageSize());
        pageResultVO.setTotal(taskEntityPageInfo.getTotal());
        log.info("TaskServiceImpl.getData{}", pageResultVO);
        return pageResultVO;
    }

    @Override
    public ResultVO<Void> updateStatus(Long id) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskStatus("已完成");
        taskEntity.setId(id);
        Date date = new Date();
        TaskParam taskParam = new TaskParam();
        taskParam.setId(id);
        List<TaskEntity> data = taskMapper.getData(taskParam);
        if (CollectionUtils.isNotEmpty(data)) {
            Date createTime = data.get(0).getCreateTime();
            String s = DateUtil.formatTimeDifference(date, createTime);
            s = s.replace("h", "小时").replace("m", "分钟").replace("s", "秒").replace(" ", "").split("and")[0];
            taskEntity.setTimeout(s);
        }
        taskEntity.setCompletionTime(date);
        int i = taskMapper.updateStatus(taskEntity);
        if (i == 1) {
            return ResultVO.buildSuccessResult(null);
        }
        log.error("TaskServiceImpl.delete{}", i);
        return ResultVO.buildFailResult("修改状态失败！");
    }

    @Override
    public ResultVO<Void> addTask(TaskEntity taskEntity) {
        taskEntity.setCreateTime(new Date());
        taskEntity.setTaskStatus("未完成");
        int i = taskMapper.insert(taskEntity);
        if (i == 1) {
            return ResultVO.buildSuccessResult(null);
        }
        log.error("TaskServiceImpl.addTask{}", i);
        return ResultVO.buildFailResult("添加任务失败！");
    }
}
