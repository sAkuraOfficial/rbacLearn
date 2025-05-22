package com.zhouzihao.rbaclearn.modules.user.repository;

import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission, String> {
    Permission findByCode(String code);
}
