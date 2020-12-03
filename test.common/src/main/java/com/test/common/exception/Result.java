package com.test.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenjie
 * @date 2020-12-03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {

    private String code;

    private String message;

}
