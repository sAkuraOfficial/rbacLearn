package com.zhouzihao.rbaclearn.modules.user.service;

import com.zhouzihao.rbaclearn.modules.user.document.Role;
import java.util.List;

public interface RoleService {
    Role create(Role role);
    Role update(Role role);
    void delete(String id);
    Role findById(String id);
    List<Role> findAll();
    void assignPermission(String roleId, String permissionId);
    void removePermission(String roleId, String permissionId);
}
