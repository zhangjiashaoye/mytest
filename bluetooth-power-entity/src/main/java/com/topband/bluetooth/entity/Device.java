package com.topband.bluetooth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class Device extends BaseEntity{

    private String id;

    private String uid;

    private String mac;

    private String sn;

    private String deviceName;

    private Integer deviceType;

    private String productId;

    private String batchNo;

    private Date lastTime;

    private Integer online;

    private Integer delFlag;

    private Date createTime;

    private Date updateTime;

}
