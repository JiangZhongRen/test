package com.jd.mapper;

import com.jd.controller.param.TaskParam;
import com.jd.service.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @className: TaskMapper
 * @description: TODO 类描述
 * @author: ext.jiangzhongren
 * @date: 2021/12/10
 **/
@Mapper
public interface TaskMapper {
    List<TaskEntity> getData(TaskParam taskParam);

    int delete(Long id);

    int updateStatus(TaskEntity taskEntity);

    int insert(TaskEntity taskEntity);
}
