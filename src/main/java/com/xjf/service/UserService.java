package com.xjf.service;

import com.xjf.repository.User;

public interface UserService {

    User getUserById(String id);

    int putUser(User user);

}
