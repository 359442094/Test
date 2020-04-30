package com.test.start.test.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2020/4/8
 */
@ToString
@Getter
@Setter
public class SendCourseAduitResult implements Serializable {

    private static final long serialVersionUID = -6190810745503173881L;
    /**
     * 0 成功 500失败
     * */
    private String msg;

    private String code;

}
