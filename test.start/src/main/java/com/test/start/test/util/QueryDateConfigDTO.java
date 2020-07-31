package com.test.start.test.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class QueryDateConfigDTO {

    private String value;

    private String label;

    private List<Children> children;

    @Data
    public class Children{

        private String value;

        private String label;

    }

}
