package com.test.controller;

import com.test.common.dto.Return;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*@Api(tags = {"配置接口"})
@PropertySource(value = "classpath:application.properties")
@ConfigurationProperties(prefix = "spring.thymeleaf")
@RestController*/
public class PropertiesController {

   /* private boolean cache;
    private String encoding;
    @Value(value = "${spring.thymeleaf.cache}")
    private String valueCache;

    @ApiOperation(value = "ConfigurationProperties注解",notes = "ConfigurationProperties注解")
    @RequestMapping(path = "/properties",method = RequestMethod.GET)
    @ResponseBody
    public Return<String> properties(){
        return new Return<String>(cache+"\t"+valueCache+"\t"+encoding);
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
*/
}
