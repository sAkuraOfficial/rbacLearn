package com.zhouzihao.rbaclearn.modules.user.controller;

import com.zhouzihao.rbaclearn.modules.user.document.Permission;
import com.zhouzihao.rbaclearn.modules.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        return permissionService.create(permission);
    }

    @PutMapping
    public Permission update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        permissionService.delete(id);
    }

    @GetMapping("/{id}")
    public Permission findById(@PathVariable String id) {
        return permissionService.findById(id);
    }

    @GetMapping
    public List<Permission> findAll() {
        return permissionService.findAll();
    }

    @PostMapping("/bindApiPath")
    public void bindApiPath(@RequestParam String permissionId, @RequestParam String apiPath) {
        permissionService.bindApiPath(permissionId, apiPath);
    }
}
