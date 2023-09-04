package com.baizhi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baizhi.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //根据姓名查找用户
    @Select("select id, name, pwd, salt, balance from user where name=#{name}")
    User findByUserName(String name);
}
