package com.zhouzihao.rbaclearn.modules.user.controller;

import com.zhouzihao.rbaclearn.modules.user.document.User;
import com.zhouzihao.rbaclearn.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/assignRole")
    public void assignRole(@RequestParam String userId, @RequestParam String roleId) {
        userService.assignRole(userId, roleId);
    }

    @PostMapping("/removeRole")
    public void removeRole(@RequestParam String userId, @RequestParam String roleId) {
        userService.removeRole(userId, roleId);
    }
}
