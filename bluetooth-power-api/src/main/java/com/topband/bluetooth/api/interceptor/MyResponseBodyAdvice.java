package com.topband.bluetooth.api.interceptor;

import com.topband.bluetooth.common.model.ResultModel0;
import com.topband.bluetooth.common.model.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author zgs
 * @date 2019-01-17
 */
@ControllerAdvice
@Slf4j
public class MyResponseBodyAdvice  implements ResponseBodyAdvice<ResultModel0> {
    
    @Autowired
    private MessageSource messageSource;
    
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ResultModel0.class.getName().equals(returnType.getMethod().getReturnType().getName());
    }

    @Override
    public ResultModel0 beforeBodyWrite(ResultModel0 body, MethodParameter returnType, MediaType selectedContentType,
                                        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest,
                                        ServerHttpResponse response) {
        ServletServerHttpRequest req = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest httpReq = req.getServletRequest();
        String language = httpReq.getHeader(SystemConstants.LANGUAGE_HEADER);
        if (StringUtils.isEmpty(language)){
            language = SystemConstants.LANGUAGE_DEFAULT;
        }
        try {
        } catch (Exception e) {
            log.error("MyResponseBodyAdvice--->转换mess异常",e);
        }
        return body;
    }

}
