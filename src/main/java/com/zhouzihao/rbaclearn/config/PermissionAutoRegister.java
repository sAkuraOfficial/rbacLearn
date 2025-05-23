package com.zhouzihao.rbaclearn.config;

import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import com.zhouzihao.rbaclearn.modules.user.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
public class PermissionAutoRegister {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PermissionRepository permissionRepository;

    @Bean
    public CommandLineRunner autoRegisterPermissions() {
        return args -> {
            String[] beanNames = applicationContext.getBeanNamesForAnnotation(RestController.class);
            // 1. 扫描所有controller接口，收集所有有效路径
            java.util.Set<String> validPaths = new java.util.HashSet<>();
            for (String beanName : beanNames) {
                Object bean = applicationContext.getBean(beanName);
                Class<?> clazz = bean.getClass();
                // 处理CGLIB代理
                if (clazz.getName().contains("$$")) {
                    clazz = clazz.getSuperclass();
                }
                String controllerName = clazz.getSimpleName();
                String classPath = "";
                RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
                if (classMapping != null && classMapping.value().length > 0) {
                    classPath = classMapping.value()[0];
                }
                for (Method method : clazz.getDeclaredMethods()) {
                    String methodPath = null;
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping m = method.getAnnotation(GetMapping.class);
                        if (m.value().length > 0) methodPath = m.value()[0];
                    } else if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping m = method.getAnnotation(PostMapping.class);
                        if (m.value().length > 0) methodPath = m.value()[0];
                    } else if (method.isAnnotationPresent(PutMapping.class)) {
                        PutMapping m = method.getAnnotation(PutMapping.class);
                        if (m.value().length > 0) methodPath = m.value()[0];
                    } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                        DeleteMapping m = method.getAnnotation(DeleteMapping.class);
                        if (m.value().length > 0) methodPath = m.value()[0];
                    } else if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping m = method.getAnnotation(RequestMapping.class);
                        if (m.value().length > 0) methodPath = m.value()[0];
                    }
                    if (methodPath != null) {
                        String fullPath = classPath.endsWith("/") || methodPath.startsWith("/") ? classPath + methodPath : classPath + "/" + methodPath;
                        validPaths.add(fullPath);
                        // 检查是否已存在
                        Permission exist = permissionRepository.findAll().stream()
                            .filter(p -> fullPath.equals(p.getApiPath()))
                            .findFirst().orElse(null);
                        if (exist == null) {
                            Permission p = new Permission();
                            p.setName(method.getName());
                            p.setCode(controllerName + "_" + method.getName());
                            p.setApiPath(fullPath);
                            p.setDescription("自动注册的接口权限:" + fullPath);
                            p.setControllerName(controllerName);
                            p.setStatus(0);
                            permissionRepository.save(p);
                            System.out.println("自动注册接口权限:" + fullPath);
                        } else if (exist.getStatus() != null && exist.getStatus() == 1) {
                            exist.setStatus(0);
                            permissionRepository.save(exist);
                            System.out.println("自动恢复逻辑删除的接口权限:" + fullPath);
                        }
                    }
                }
            }
            // 2. 逻辑删除数据库中无效的权限
            for (Permission p : permissionRepository.findAll()) {
                if (p.getStatus() == null || p.getStatus() == 0) {
                    if (p.getApiPath() != null && !validPaths.contains(p.getApiPath()) && !"*".equals(p.getApiPath())) {
                        p.setStatus(1);
                        permissionRepository.save(p);
                        System.out.println("逻辑删除无效权限:" + p.getApiPath());
                    }
                }
            }
        };
    }
}
