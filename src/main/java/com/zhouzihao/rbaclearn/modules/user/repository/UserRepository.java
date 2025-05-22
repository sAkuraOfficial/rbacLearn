package com.zhouzihao.rbaclearn.modules.user.repository;

import com.zhouzihao.rbaclearn.modules.user.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
