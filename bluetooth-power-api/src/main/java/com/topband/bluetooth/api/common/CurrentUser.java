package com.topband.bluetooth.api.common;


import com.topband.bluetooth.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 当前登录用户信息.
 * 
 * @author Administrator
 *
 */
@Data
public class CurrentUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;
    
    //access token
    private String token;
    
    //refresh token
    private String refreshToken;

}
