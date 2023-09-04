package com.baizhi.service.impl;

import com.baizhi.dao.UserMapper;
import com.baizhi.model.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        //对密码进行加盐md5加密
        String salt = SaltUtils.getSalt(8);
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getPwd(),salt,1024);
        user.setPwd(md5Hash.toHex());
        System.out.println(user);

        this.userMapper.insert(user);
    }

    @Override
    public User getUserByName(String username) {
        return this.userMapper.findByUserName(username);
    }

    @Override
    public void updateUser(User user) {
        this.userMapper.updateById(user);
    }

}
