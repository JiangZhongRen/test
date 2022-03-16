package com.jd.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jd.mapper.SkuJdMapper;
import com.jd.mapper.TaskMapper;
import com.jd.service.PaChongService;
import com.jd.service.entity.PaChongEntity;
import com.jd.service.entity.TaskEntity;
import com.jd.utils.HttpUtil;
import com.jd.utils.OkHttpUtils;
import com.jd.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @className: PaChongServiceImpl
 * @author: ext.jiangzhongren
 * @date: 2022/1/26
 **/
@Service
@Slf4j
public class PaChongServiceImpl implements PaChongService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SkuJdMapper skuJdMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<Void> addPachongTask(PaChongEntity paChongEntity) {
        //todo 添加任务数据
//        TaskEntity taskEntity = new TaskEntity();
//        taskEntity.setTaskStatus("未完成");
//        taskMapper.insert();
        //todo 任务跑出来添加数据
        String sync = HttpUtil.methodGet(paChongEntity.getUrl());
        sync = sync.replaceAll("jQuery7009518\\(", "").replace(")", "");
        JSONObject object = JSON.parseObject(sync);
        Set<String> keys = object.keySet();
        List<Map<String, String>> hashMaps = Lists.newArrayList();
        keys.forEach(key -> {
            JSONArray objects = JSON.parseArray(object.get(key).toString());
            objects.forEach(obj -> {
                JSONObject object1 = JSON.parseObject(obj.toString());
                Set<String> strings = object1.keySet();
                Map<String, String> map = new HashMap<>();
                strings.forEach(str -> {
                    if ("image_url".equals(str)) {
                        map.put(str, "https://img14.360buyimg.com/n2/" + object1.get(str).toString());
                    } else {
                        map.put(str, object1.get(str).toString());
                    }
                });
                log.info("map{}", JSON.toJSONString(map));
                hashMaps.add(map);
            });
        });
        skuJdMapper.bachInsert(hashMaps);
//        //todo 修改任务状态
//        taskMapper.updateStatus();
        return ResultVO.buildSuccessResult(null);
    }
}
