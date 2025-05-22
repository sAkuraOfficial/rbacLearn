package com.zhouzihao.rbaclearn.modules.user.service.impl;

import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import com.zhouzihao.rbaclearn.modules.user.repository.PermissionRepository;
import com.zhouzihao.rbaclearn.modules.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission create(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void delete(String id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Permission findById(String id) {
        Optional<Permission> p = permissionRepository.findById(id);
        return p.orElse(null);
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public void bindApiPath(String permissionId, String apiPath) {
        Permission p = findById(permissionId);
        if (p != null) {
            p.setApiPath(apiPath);
            permissionRepository.save(p);
        }
    }
}
