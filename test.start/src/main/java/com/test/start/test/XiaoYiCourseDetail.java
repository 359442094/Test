package com.test.start.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CJ
 * @date 2019/10/14
 */
@ToString
@Getter
@Setter
public class XiaoYiCourseDetail implements Serializable {

    private static final long serialVersionUID = -2009426713491210474L;

    private Integer id;
    private Integer courseId;
    private String lessonId;
    private String lessonName;
    private String code;
    private Integer lessonNum;
    private Date lessonDate;
    private Date startTime;
    private Date endTime;
    private String orgCode;

}
