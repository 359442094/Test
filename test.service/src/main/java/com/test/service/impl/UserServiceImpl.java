package com.test.service.impl;

import com.test.common.util.ConvertUtil;
import com.test.model.domain.User;
import com.test.model.domain.UserExample;
import com.test.model.persistence.UserMapper;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public com.test.common.dto.User login(User user) {
        com.test.common.dto.User resultUser=null;
        UserExample userExample=new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName()).andPwdEqualTo(user.getPwd());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()>0){
            resultUser = ConvertUtil.convert(user);
        }
        return resultUser;
    }

}
