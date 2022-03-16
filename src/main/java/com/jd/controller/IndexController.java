package com.jd.controller;

import com.google.common.collect.Lists;
import com.jd.controller.param.TaskParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: IndexController
 * @author: ext.jiangzhongren
 * @date: 2021/12/10
 **/
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    public static void main(String[] args) {
        List<TaskParam> list = Lists.newArrayList(new TaskParam(1L,2,2));
        list.add(new TaskParam(9L,92,12));
        list.add(new TaskParam(4L,9,19));
        list.add(new TaskParam(1L,22,10));
        list.add(new TaskParam(8L,72,1));
        List<TaskParam> collect = list.stream().sorted(Comparator.comparing(TaskParam::getId).reversed()).collect(Collectors.toList());
        System.out.println(collect);
    }

    @RequestMapping("/workbench/task")
    public String console() {
        return "workbench/task";
    }

    @RequestMapping("/procenter")
    public String proCenter() {
        return "procenter";
    }

    @RequestMapping("/workbench/address")
    public String address() {
        return "workbench/address";
    }

    @RequestMapping("/workbench/navigation")
    public String navigation() {
        return "workbench/navigation";
    }

    @RequestMapping("/workbench/children/pachong")
    public String pachong() {
        return "workbench/children/pachong";
    }
}
