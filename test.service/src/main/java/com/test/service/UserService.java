package com.test.service;

import com.test.model.domain.User;

import java.util.List;

public interface UserService {

    com.test.common.dto.User login(User user);

    List<User> users();

}
