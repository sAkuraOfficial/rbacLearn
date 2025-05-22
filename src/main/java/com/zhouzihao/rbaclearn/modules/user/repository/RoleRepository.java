package com.zhouzihao.rbaclearn.modules.user.repository;

import com.zhouzihao.rbaclearn.modules.user.document.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByCode(String code);
}
