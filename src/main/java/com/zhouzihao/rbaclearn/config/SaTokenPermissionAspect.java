package com.zhouzihao.rbaclearn.config;

import cn.dev33.satoken.stp.StpUtil;
import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import com.zhouzihao.rbaclearn.modules.user.document.Role;
import com.zhouzihao.rbaclearn.modules.user.document.User;
import com.zhouzihao.rbaclearn.modules.user.repository.PermissionRepository;
import com.zhouzihao.rbaclearn.modules.user.repository.RoleRepository;
import com.zhouzihao.rbaclearn.modules.user.repository.UserRepository;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class SaTokenPermissionAspect {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PermissionRepository permissionRepository;

    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    public void checkPermission(JoinPoint joinPoint) {
        // 获取当前请求的 API 路径
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        String path = null;
        if (mapping != null && mapping.value().length > 0) {
            path = mapping.value()[0];
        }
        if (path == null) {
            // 兼容 GetMapping/PostMapping 等
            if (method.isAnnotationPresent(org.springframework.web.bind.annotation.GetMapping.class)) {
                org.springframework.web.bind.annotation.GetMapping getMapping = method
                        .getAnnotation(org.springframework.web.bind.annotation.GetMapping.class);
                if (getMapping.value().length > 0) {
                    path = getMapping.value()[0];
                }
            } else if (method.isAnnotationPresent(org.springframework.web.bind.annotation.PostMapping.class)) {
                org.springframework.web.bind.annotation.PostMapping postMapping = method
                        .getAnnotation(org.springframework.web.bind.annotation.PostMapping.class);
                if (postMapping.value().length > 0) {
                    path = postMapping.value()[0];
                }
            } else if (method.isAnnotationPresent(org.springframework.web.bind.annotation.PutMapping.class)) {
                org.springframework.web.bind.annotation.PutMapping putMapping = method
                        .getAnnotation(org.springframework.web.bind.annotation.PutMapping.class);
                if (putMapping.value().length > 0) {
                    path = putMapping.value()[0];
                }
            } else if (method.isAnnotationPresent(org.springframework.web.bind.annotation.DeleteMapping.class)) {
                org.springframework.web.bind.annotation.DeleteMapping deleteMapping = method
                        .getAnnotation(org.springframework.web.bind.annotation.DeleteMapping.class);
                if (deleteMapping.value().length > 0) {
                    path = deleteMapping.value()[0];
                }
            }
        }
        if (path == null)
            return; // 无法获取路径则跳过
        // 获取当前登录用户
        Object loginId = StpUtil.getLoginIdDefaultNull();
        if (loginId == null)
            return; // 未登录
        User user = userRepository.findById(loginId.toString()).orElse(null);
        if (user == null)
            return;
        // 获取用户所有角色
        List<Role> roles = roleRepository.findAllById(user.getRoleIds());
        Set<String> permissionIds = new HashSet<>();
        for (Role role : roles) {
            if (role.getPermissionIds() != null) {
                permissionIds.addAll(role.getPermissionIds());
            }
        }
        // 获取所有权限
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        // 获取类上的 @RequestMapping 路径前缀
        String classPath = "";
        Class<?> clazz = method.getDeclaringClass();
        RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
        if (classMapping != null && classMapping.value().length > 0) {
            classPath = classMapping.value()[0];
        }
        // 拼接完整路径
        String fullPath = classPath.endsWith("/") || path.startsWith("/") ? classPath + path : classPath + "/" + path;
        final String finalPath = fullPath;
        boolean hasPermission = permissions.stream()
                .anyMatch(p -> "*".equals(p.getApiPath()) || finalPath.equals(p.getApiPath()));
        if (!hasPermission) {
            throw new RuntimeException("无权限访问该接口: " + finalPath);
        }
    }
}
