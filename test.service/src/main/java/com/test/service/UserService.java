package com.test.service;

import com.test.model.domain.Test;
import com.test.model.domain.User;

import java.util.List;

public interface UserService {

    Test login(Test test);

    List<Test> tests();

}
