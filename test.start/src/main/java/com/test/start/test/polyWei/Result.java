package com.test.start.test.polyWei;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author CJ
 * @date 2020/6/3
 */
@ToString
@Getter
@Setter
public class Result {

    private String error;

    private List<Data> data;

}
