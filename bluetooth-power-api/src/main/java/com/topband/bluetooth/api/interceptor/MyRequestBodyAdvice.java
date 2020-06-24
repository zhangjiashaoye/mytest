package com.topband.bluetooth.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Type;


@ControllerAdvice
public class MyRequestBodyAdvice extends RequestBodyAdviceAdapter implements RequestBodyAdvice {

    final Logger log = LoggerFactory.getLogger(RequestBodyAdviceAdapter.class);
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
    	return methodParameter.getMethod().getParameterTypes().getClass().isAssignableFrom(JSONObject.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
	return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
    	if(body != null) {
    		log.info(body.toString());
    	}
	return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
	return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }


}
