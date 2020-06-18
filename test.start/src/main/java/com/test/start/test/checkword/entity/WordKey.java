package com.test.start.test.checkword.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author CJ
 * @date 2020/6/18
 */
@ToString
@Getter
@Setter
public class WordKey implements Serializable {

    private static final long serialVersionUID = 8357431612209380550L;

    private Integer wordId;

    private String wordKey;

    private String isDelete;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

}
