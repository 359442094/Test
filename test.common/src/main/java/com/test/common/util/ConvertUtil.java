package com.test.common.util;

import com.test.common.dto.User;

public class ConvertUtil {

    public static User convert(com.test.model.domain.User user){
        User result=new User();
        result.setUsername(user.getName());
        result.setPassword(user.getPwd());
        return result;
    }

}
