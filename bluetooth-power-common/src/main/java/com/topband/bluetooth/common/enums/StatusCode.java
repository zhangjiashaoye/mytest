package com.topband.bluetooth.common.enums;

/**
 * @author ludi
 * @version 1.0
 * @date 2020/3/24 11:04
 * @remark
 */
public enum StatusCode {
    SUCCESS(0),//操作成功
    TOKEN_ACCOUNTS_FORBID(50),//账号禁止使用
    TOKEN_NOTNEWEST_LOGIN(49),//"账号在其他地方登录，强制下线
    TOKEN_TIMEOUT(17),//未登录或者会话超时
    SYSTEM_ERROR(1),//系统错误
    URL_FORBID(2),//权限不够

    FAIL(200304),//操作失败
    PARAMETER_LACK(200001),// 缺少必填参数
    PARAMETER_ERROR(200008),//参数错误


    ;

    StatusCode(Integer _code) {
        this.code = _code;
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }
}
