package com.test.common.enums;

import lombok.*;

/**
 * @author chenjie
 * @version 1.0
 * @date 2021/5/23 11:21
 */
public enum TestEnum {

    TEST_ENUM_1(1,"TEST1"),TEST_ENUM_2(2,"TEST2");

    TestEnum() {

    }

    TestEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    private Integer index;

    private String name;

    public static String getNameByCode(Integer code){
        TestEnum[] values = TestEnum.values();
        for (TestEnum value : values) {
            if(code == value.getIndex()){
                return value.getName();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String name = TestEnum.getNameByCode(1);
        System.out.println(name);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
