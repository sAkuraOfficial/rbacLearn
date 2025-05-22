package com.zhouzihao.rbaclearn.modules.user.controller;

import com.zhouzihao.rbaclearn.modules.user.document.Role;
import com.zhouzihao.rbaclearn.modules.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.create(role);
    }

    @PutMapping
    public Role update(@RequestBody Role role) {
        return roleService.update(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        roleService.delete(id);
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable String id) {
        return roleService.findById(id);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @PostMapping("/assignPermission")
    public void assignPermission(@RequestParam String roleId, @RequestParam String permissionId) {
        roleService.assignPermission(roleId, permissionId);
    }

    @PostMapping("/removePermission")
    public void removePermission(@RequestParam String roleId, @RequestParam String permissionId) {
        roleService.removePermission(roleId, permissionId);
    }
}
