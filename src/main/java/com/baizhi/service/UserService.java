package com.baizhi.service;

import com.baizhi.model.User;

public interface UserService {
    //注册
    void save(User user);
    //根据用户名查用户
    User getUserByName(String username);
    //修改用户数据
    void updateUser(User user);
}
