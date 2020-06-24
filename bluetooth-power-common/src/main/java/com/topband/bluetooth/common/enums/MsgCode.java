package com.topband.bluetooth.common.enums;

/**
 * @author ludi
 * @version 1.0
 * @date 2020/3/24 11:13
 * @remark
 */
public enum MsgCode {
    SUCCESS("common.success"),//操作成功
    FAIL("common.fail"),//操作失败
    PARAMETER_LACK("common.parameter.lack"),// 缺少必填参数
    PARAMETER_ERROR("common.parameter.error"),//参数错误
    TOKEN_ACCOUNTS_FORBID("common.token.accounts.forbid"),//账号禁止使用
    TOKEN_NOTNEWEST_LOGIN("common.token.notNewestLogin"),//"账号在其他地方登录，强制下线
    TOKEN_TIMEOUT("common.token.timeout"),//未登录或者会话超时
    TOKEN_ERROR("common.token.error"),//令牌错误
    SYSTEM_ERROR("common.system.error"),//系统错误
    URL_FORBID("common.url.forbid"),//系统错误


    ;
    MsgCode(String  _code) {
        this.code = _code;
    }
    private String code;

    public String getMsgCode() {
        return code;
    }
}
