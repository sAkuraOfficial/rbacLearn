package com.zhouzihao.rbaclearn.modules.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;

import com.zhouzihao.rbaclearn.modules.user.document.User;
import com.zhouzihao.rbaclearn.modules.user.service.UserService;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            StpUtil.login(user.getId());
            return StpUtil.getTokenValue();
        }
        return "用户名或密码错误";
    }

    @GetMapping("/isLogin")
    public boolean isLogin(HttpServletRequest request) {
        // // 打印所有header
        // Enumeration<String> headerNames = request.getHeaderNames();
        // System.out.println("所有请求头：");
        // while (headerNames.hasMoreElements()) {
        //     String headerName = headerNames.nextElement();
        //     String headerValue = request.getHeader(headerName);
        //     System.out.println(headerName + ": " + headerValue);
        // }
        // System.out.println("用户token检查登录:" + StpUtil.getTokenValue());
        return StpUtil.isLogin();
    }

    @PostMapping("/logout")
    public void logout() {
        StpUtil.logout();
        System.out.println("用户已登出");
    }
}
