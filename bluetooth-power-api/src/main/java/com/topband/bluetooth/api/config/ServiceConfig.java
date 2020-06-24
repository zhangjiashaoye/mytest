package com.topband.bluetooth.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liuchuang
 * @version 1.0
 * @description TODO
 * @date 2019/12/13 15:29
 * @modify
 */
@Component
public class ServiceConfig {
    @Value("${example.property}")
    private String exampleProperty="";

    public String getExampleProperty(){
        return exampleProperty;
    }
}