package com.zhouzihao.rbaclearn.modules.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@SaCheckLogin
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping("/hello")
    public String hello() {
        System.out.println(StpUtil.isLogin());
        return "hello world";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello world";
    }

}
