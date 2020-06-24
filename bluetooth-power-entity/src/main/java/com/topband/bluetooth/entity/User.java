package com.topband.bluetooth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class User {
    private String id;

    private String userName;

    private String password;

    private String phone;

    private String email;

    private Integer userType;

    private Integer delFlag;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;


}
