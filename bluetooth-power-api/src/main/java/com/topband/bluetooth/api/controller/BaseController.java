package com.topband.bluetooth.api.controller;

import com.topband.bluetooth.api.common.CurrentUser;
import com.topband.bluetooth.api.common.RedisUtils;
import com.topband.bluetooth.api.common.ServletUtils;
import com.topband.bluetooth.common.enums.CommonError;
import com.topband.bluetooth.common.model.ResultModel0;
import com.topband.bluetooth.common.model.SystemConstants;
import com.topband.bluetooth.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ludi
 * @version 1.0
 * @date 2020/3/17 17:33
 * @remark
 */
public class BaseController {

    public long expireTime = 120;
    public long refreshExpire = 43200;
    public long checkExpire = 43200;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    protected RedisUtils<Object> redisUtils;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    protected void refreshToken(String refreshToken, CurrentUser user) {
        String refreshKey = SystemConstants.REFRESH_TOKEN + refreshToken;
        redisUtils.setEx(refreshKey, user, expireTime, TimeUnit.MINUTES);
    }

    protected void resetStaff(User user) {
        CurrentUser currentUser = getCurrentUser();
        if (currentUser == null) {
            return;
        }
        User login = currentUser.getUser();
        if (login == null || login.getId() == user.getId()) {
            return;
        }
        user.setPassword(null);
        currentUser.setUser(user);
        restCurrentUser(currentUser);
    }

    protected void forceLogout(String userId, String token, boolean isApp) {
        cacheToken(userId, token, isApp);
    }

    private String getToken() {
        return ServletUtils.getRequest().getHeader(SystemConstants.TOKEN_HEADER);
    }

    protected String getLanguage() {
        return ServletUtils.getRequest().getHeader(SystemConstants.LANGUAGE_HEADER);
    }

    private CurrentUser getCurrentUser() {
        String ip = ServletUtils.getIpAddr();
        String token = getToken();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String tokenKey = SystemConstants.TOKEN_CACHE_PREFIX + ip + token;
        CurrentUser currentUser = (CurrentUser) redisUtils.get(tokenKey);
        if (currentUser == null) {
            tokenKey = SystemConstants.TOKEN_CACHE_PREFIX + token;
            currentUser = (CurrentUser) redisUtils.get(tokenKey);
        }
        return currentUser;
    }


    protected User getLoginUser() {
        CurrentUser currentUser = getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return currentUser.getUser();
    }


    private void restCurrentUser(CurrentUser currentUser) {
        String token = getToken();
        //web端登录
        String ip = ServletUtils.getIpAddr();
        String tokenKey = SystemConstants.TOKEN_CACHE_PREFIX + ip + token;
        redisUtils.setEx(tokenKey, currentUser, expireTime, TimeUnit.MINUTES);
    }


    private String getMessage(Locale locale, String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, locale);
        } catch (NoSuchMessageException ex) {
            // log.error("MessageSource 缺少 "+msgKey);
            return "";
        }
    }

    protected String getMessage(String msgKey) {

        Locale locale = ServletUtils.getLocale();
        return getMessage(locale, msgKey);
    }

    protected <T> ResultModel0<T> getResultMode(int status, String msgCode, T data) {
        ResultModel0 response = new ResultModel0();
        response.setStatus(status);
        response.setMessage(getMessage(msgCode));
        response.setData(data);
        return response;
    }

    protected <T> ResultModel0<T> succeed(T data) {
        return getResultMode(CommonError.STATUS_0.getStatus(), CommonError.STATUS_0.getMsgCode(), data);
    }

    protected <T> ResultModel0<T> fail(T data) {
        return getResultMode(CommonError.STATUS_200304.getStatus(), CommonError.STATUS_200304.getMsgCode(), data);
    }

    protected <T> ResultModel0<T> error(T data) {
        return getResultMode(CommonError.STATUS_500301.getStatus(), CommonError.STATUS_500301.getMsgCode(), data);
    }

    protected <T> ResultModel0<Map<String, Object>> succeedPage(Collection<T> records, long total) {
        ResultModel0<Map<String, Object>> response = new ResultModel0();
        response.setStatus(CommonError.STATUS_0.getStatus());
        response.setMessage(getMessage(CommonError.STATUS_200304.getMsgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("rows", records);
        dataMap.put("total", total);
        response.setData(dataMap);
        return response;
    }

//    int checkCode(boolean isDelete, String code, SendSmsMode mode) {
//        Integer infoType = mode.getInfoType();
//        if (infoType == null) {
//            infoType = 1;
//        }
//        String checkObj = mode.getSendMode();
//        String companyId = mode.getCompanyId();
//        String type = mode.getType();
//        String key = SystemConstants.KEY_HEAD_CODE_SMS + checkObj + "_" + companyId + "_" + infoType + "_" + type;
//        String check = SystemConstants.KEY_HEAD_CHECK_CODE_SMS + checkObj + code + "_" + companyId + "_" + infoType + "_" + type;
//        int expire = SystemConstants.MEMCACHED_CODE_TIME_EMAIL;
//
//
//        String checked = (String) redisUtils.get(check);
//        if ("checked".equals(checked)) {
//            return SystemConstants.STATUS_CODE_LOST; //验证码已使用
//        }
//        logger.info("key:{}", key);
//        if (!redisUtils.hasKey(key)) {
//            return SystemConstants.STATUS_CODE_LOST; //验证码过期
//        }
//
//        String cacheCode = (String) redisUtils.hGet(key, SystemConstants.KEY_CODE);
//        logger.info("redis获取验证码：{} ,前端验证码:{}", cacheCode, code);
//        if (!code.equals(cacheCode)) {
//            Integer tryCount = parseInt((String) redisUtils.hGet(key, SystemConstants.KEY_TRY));
//            tryCount = tryCount == null ? 0 : tryCount;
//            tryCount++;
//            if (tryCount >= SystemConstants.ERROR_COUNT_MAX_LIMIT) {
//                redisUtils.expire(key, 1, TimeUnit.SECONDS);
//            } else {
//                redisUtils.hPut(key, SystemConstants.KEY_TRY, tryCount + "");
//            }
//            return SystemConstants.STATUS_CODE_ERROR;  //验证码不正确
//        }
//        if (isDelete) {
//            redisUtils.setEx(check, "checked", expire, TimeUnit.MINUTES);
//        }
//        return 0;
//    }

    public void cacheToken(String userId, String token, boolean isApp) {
        String key = SystemConstants.WEB_LOGIN_TOKEN_PREFIX + userId;
        if(isApp) {
            key = SystemConstants.APP_LOGIN_TOKEN_PREFIX + userId;
        }
        Object lastToken = redisUtils.get(key);
        if(lastToken != null && !"".equals(lastToken)) {
            redisUtils.setEx(SystemConstants.KNOCT_OUT_PREFIX + lastToken, SystemConstants.FORCE_LOGOUT, 120, TimeUnit.MINUTES);
        }
        redisUtils.setEx(key, token, 120, TimeUnit.MINUTES);
    }
}
