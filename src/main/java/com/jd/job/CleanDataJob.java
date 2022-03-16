package com.jd.job;

import com.google.common.collect.Lists;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * @className: CleanDataJob
 * @description: TODO 类描述
 * @author: ext.jiangzhongren
 * @date: 2022/1/4
 **/
//在要执行定时任务的类上加上@Component
//@Component
public class CleanDataJob {
    //在要执行的方法上加上@Scheduled
    //表示方法执行完成后5秒
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayJob() {
        System.out.println("fixedDelay 每隔5秒执行一次" + new Date());
    }

    //表示每隔三秒
    @Scheduled(fixedRate = 3000)
    public void fixedRate() {
        System.out.println("fixedRate 每隔三秒执行一次" + new Date());
    }

    //表示每天定时执行
    @Scheduled(cron = "0 0,31 14 * * ? ")
    public void cronJob() {
        System.out.println("cronJob 执行" + new Date());
    }
}
