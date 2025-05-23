package com.zhouzihao.rbaclearn.modules.user.service.impl;

import com.zhouzihao.rbaclearn.modules.user.document.Role;
import com.zhouzihao.rbaclearn.modules.user.repository.RoleRepository;
import com.zhouzihao.rbaclearn.modules.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findById(String id) {
        Optional<Role> r = roleRepository.findById(id);
        return r.orElse(null);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void assignPermission(String roleId, String permissionId) {
        Role r = findById(roleId);
        if (r != null) {
            if (r.getPermissionIds() == null) {
                r.setPermissionIds(new java.util.ArrayList<>());
            }
            if (!r.getPermissionIds().contains(permissionId)) {
                r.getPermissionIds().add(permissionId);
                roleRepository.save(r);
            }
        }
    }

    @Override
    public void removePermission(String roleId, String permissionId) {
        Role r = findById(roleId);
        if (r != null && r.getPermissionIds().contains(permissionId)) {
            r.getPermissionIds().remove(permissionId);
            roleRepository.save(r);
        }
    }
}
