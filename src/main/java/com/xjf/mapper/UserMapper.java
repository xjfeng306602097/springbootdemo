package com.xjf.mapper;

import com.xjf.repository.User;

public interface UserMapper {
    int deleteByPrimaryKey(String tUserid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String tUserid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}