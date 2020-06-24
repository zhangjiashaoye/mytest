package com.topband.bluetooth.common.util;


import com.topband.bluetooth.common.enums.MsgCode;
import com.topband.bluetooth.common.enums.StatusCode;
import com.topband.bluetooth.common.model.ResultModel;
import com.topband.bluetooth.common.model.ResultModel0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 返回消息帮助类
 *
 * @author ludycool
 * @create 2018-10-26 9:58
 */
public class ResultModeHelper {

    public  static <T> ResultModel0<T> getResultMode0(int status, String msgCode, T data) {
    ResultModel0 response = new ResultModel0();
    response.setStatus(status);
    response.setMessage(msgCode);
    response.setData(data);
    return response;
}
    public  static ResultModel getResultMode(int status, String msgCode, Object data) {
        ResultModel response = new ResultModel();
        response.setStatus(status);
        response.setMessage(msgCode);
        response.setData(data);
        return response;
    }
    /**
     * 缺少参数
     * @param data
     * @param <T>
     * @return
     */
    public  static  <T> ResultModel0<T> parameterLack(T data) {
        return getResultMode0(StatusCode.PARAMETER_LACK.getCode(), MsgCode.PARAMETER_LACK.getMsgCode(),data);
    }

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public  static <T> ResultModel0<T> succeed(T data) {
        return getResultMode0(StatusCode.SUCCESS.getCode(), MsgCode.SUCCESS.getMsgCode(),data);
    }

    /**
     * 失败
     * @param data
     * @param <T>
     * @return
     */
    public  static <T> ResultModel0<T> fail(T data) {
        return getResultMode0(StatusCode.FAIL.getCode(), MsgCode.FAIL.getMsgCode(),data);
    }

    /**
     * 错误
     * @param data
     * @param <T>
     * @return
     */
    public  static <T> ResultModel0<T> error(T data) {
        return getResultMode0(StatusCode.SYSTEM_ERROR.getCode(), MsgCode.SYSTEM_ERROR.getMsgCode(),data);
    }
    /**
     * 错误
     * @param
     * @param
     * @return
     */
    public  static ResultModel error() {
        return getResultMode(StatusCode.SYSTEM_ERROR.getCode(), MsgCode.SYSTEM_ERROR.getMsgCode(),null);
    }
    /**
     * 分页数据
     * @param records
     * @param total
     * @param <T>
     * @return
     */
    public  static <T> ResultModel0<Map<String,Object>> succeedPage(Collection<T> records, long total) {
        ResultModel0<Map<String,Object>> response = new ResultModel0();
        response.setStatus(StatusCode.SUCCESS.getCode());
        response.setMessage(MsgCode.SUCCESS.getMsgCode());
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("rows",records);
        dataMap.put("total",total);
        response.setData(dataMap);
        return  response;
    }

}