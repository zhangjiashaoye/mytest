package com.topband.bluetooth.api.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableAsync
public class AnnotationRegisterListener {

//    @Autowired
//    private RabbitTemplate rabbitTemplate;


    @EventListener
    // 开启异步支持
    //@Async
    public void regist(UserRegistEvent userRegistEvent) {
    }






}
