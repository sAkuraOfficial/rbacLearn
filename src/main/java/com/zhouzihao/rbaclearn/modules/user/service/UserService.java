package com.zhouzihao.rbaclearn.modules.user.service;

import com.zhouzihao.rbaclearn.modules.user.document.User;
import java.util.List;

public interface UserService {
    User create(User user);
    User update(User user);
    void delete(String id);
    User findById(String id);
    List<User> findAll();
    void assignRole(String userId, String roleId);
    void removeRole(String userId, String roleId);
    User findByUsername(String username);
}
