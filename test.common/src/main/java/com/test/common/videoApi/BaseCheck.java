package com.test.common.videoApi;

import com.test.common.exception.ServiceException;
import com.test.common.util.ExceptionConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class BaseCheck implements Serializable {

    private static final long serialVersionUID = 2878068229512728433L;

    public void checkJson(String json){
        if(StringUtils.isEmpty(json)){
            throw new ServiceException(ExceptionConstant.EXCEPTION_PARMSISNULL,"返回JSON参数为空");
        }
    }

}
