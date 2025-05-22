package com.zhouzihao.rbaclearn.modules.user.service;

import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import java.util.List;

public interface PermissionService {
    Permission create(Permission permission);
    Permission update(Permission permission);
    void delete(String id);
    Permission findById(String id);
    List<Permission> findAll();
    void bindApiPath(String permissionId, String apiPath);
}
