package com.test.start.test.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final List<User> users = new ArrayList<User>() {
        {
            add(new User("陈杰", "男", 20));
        }
    };

    private String name;
    private String sex;
    private Integer age;

}
