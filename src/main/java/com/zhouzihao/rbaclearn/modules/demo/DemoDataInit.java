package com.zhouzihao.rbaclearn.modules.demo;

import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import com.zhouzihao.rbaclearn.modules.user.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataInit {
    @Bean
    public CommandLineRunner demoPermissionInit(PermissionRepository permissionRepository) {
        return args -> {
            // 检查是否已存在该权限
            if (permissionRepository.findByCode("DEMO_HELLO") == null) {
                Permission p = new Permission();
                p.setName("demo-hello接口");
                p.setCode("DEMO_HELLO");
                p.setApiPath("/hello");
                p.setDescription("访问demo hello world接口的权限");
                permissionRepository.save(p);
                System.out.println("已初始化 demo-hello 权限");
            }
        };
    }
}
