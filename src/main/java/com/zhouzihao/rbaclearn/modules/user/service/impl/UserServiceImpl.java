package com.zhouzihao.rbaclearn.modules.user.service.impl;

import com.zhouzihao.rbaclearn.modules.user.document.User;
import com.zhouzihao.rbaclearn.modules.user.repository.UserRepository;
import com.zhouzihao.rbaclearn.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(String id) {
        Optional<User> u = userRepository.findById(id);
        return u.orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void assignRole(String userId, String roleId) {
        User u = findById(userId);
        if (u != null && !u.getRoleIds().contains(roleId)) {
            u.getRoleIds().add(roleId);
            userRepository.save(u);
        }
    }

    @Override
    public void removeRole(String userId, String roleId) {
        User u = findById(userId);
        if (u != null && u.getRoleIds().contains(roleId)) {
            u.getRoleIds().remove(roleId);
            userRepository.save(u);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
