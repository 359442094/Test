package com.test.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2020/1/10
 */
@ToString
@Getter
@Setter
public class LoginTest implements Serializable {

    private static final long serialVersionUID = 7615419674501989359L;

    private String sessionId;

    private Integer userId;

    private String userName;

    private String passWord;

}
