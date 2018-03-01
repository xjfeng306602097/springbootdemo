package com.xjf.service.impl;

import com.xjf.mapper.UserMapper;
import com.xjf.repository.User;
import com.xjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;

    @Override
    public User getUserById(String id) {
        return userDao.selectByPrimaryKey(id);
    }
}
