package com.jd.service;

import com.jd.controller.param.TaskParam;
import com.jd.service.entity.TaskEntity;
import com.jd.vo.PageResultVO;
import com.jd.vo.ResultVO;

public interface TaskService {
    ResultVO<Void> delete(Long id);

    PageResultVO<TaskEntity> getData(TaskParam taskParam);

    ResultVO<Void> updateStatus(Long id);

    ResultVO<Void> addTask(TaskEntity taskEntity);
}
