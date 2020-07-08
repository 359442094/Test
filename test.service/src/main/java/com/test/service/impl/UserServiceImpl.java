package com.test.service.impl;

import com.test.model.domain.Test;
import com.test.model.domain.TestExample;
import com.test.model.persistence.TestMapper;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public Test login(Test test) {
        Test resultUser=null;
        TestExample testExample=new TestExample();
        testExample.createCriteria().andNameEqualTo(test.getName()).andPwdEqualTo(test.getPwd());
        List<Test> users = testMapper.selectByExample(testExample);
        if(users.size()>0){
            resultUser = test;
        }
        return resultUser;
    }

    @Override
    public List<Test> tests() {
        return  testMapper.selectByExample(new TestExample());
    }

}
