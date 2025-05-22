package com.zhouzihao.rbaclearn;

import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import com.zhouzihao.rbaclearn.modules.user.document.Role;
import com.zhouzihao.rbaclearn.modules.user.document.User;
import com.zhouzihao.rbaclearn.modules.user.repository.PermissionRepository;
import com.zhouzihao.rbaclearn.modules.user.repository.RoleRepository;
import com.zhouzihao.rbaclearn.modules.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class RbacLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacLearnApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataInitializer(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            if (userRepository.count() == 0 && roleRepository.count() == 0 && permissionRepository.count() == 0) {
                // 创建超级管理员权限
                Permission allPermission = new Permission();
                allPermission.setName("全部权限");
                allPermission.setCode("ALL");
                allPermission.setApiPath("*");
                allPermission.setDescription("系统全部权限");
                permissionRepository.save(allPermission);

                // 创建超级管理员角色
                Role adminRole = new Role();
                adminRole.setName("超级管理员");
                adminRole.setCode("admin");
                adminRole.setDescription("系统超级管理员角色");
                adminRole.setPermissionIds(Collections.singletonList(allPermission.getId()));
                roleRepository.save(adminRole);

                // 创建超级管理员账号
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRoleIds(Collections.singletonList(adminRole.getId()));
                userRepository.save(admin);

                System.out.println("已自动初始化超级管理员账号：admin/admin123");
            }
        };
    }

}
