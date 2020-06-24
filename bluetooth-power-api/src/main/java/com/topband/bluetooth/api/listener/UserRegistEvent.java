package com.topband.bluetooth.api.listener;

import org.springframework.context.ApplicationEvent;

public class UserRegistEvent extends ApplicationEvent {

    private String username;

    private Integer type;

    private String language;

    private String domain;

    public UserRegistEvent(Object source , String username, Integer type, String language, String domain) {
        super(source);
        this.username = username;
        this.type = type;
        this.language = language;
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public Integer getType() {
        return type;
    }

    public String getLanguage() {
        return language;
    }

    public String getDomain() {
        return domain;
    }
}
