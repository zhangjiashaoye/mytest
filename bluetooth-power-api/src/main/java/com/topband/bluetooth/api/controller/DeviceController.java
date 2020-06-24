package com.topband.bluetooth.api.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topband.bluetooth.common.model.ResultModel0;
import com.topband.bluetooth.common.util.ResultModeHelper;
import com.topband.bluetooth.entity.Device;
import com.topband.bluetooth.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(description = "设备表 接口")
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController{

    @Autowired
    private DeviceService deviceService;

    @ApiOperation("经销商设备-所有的设备")
    @PostMapping(value = "/allList")
    public ResultModel0<Map<String, Object>> allList(@RequestBody JSONObject params) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageNumber", StringUtils.isEmpty(params.getString("pageNumber")) ? 1 : params.getInteger("pageNumber"));
        paramMap.put("pageSize", StringUtils.isEmpty(params.getString("pageSize")) ? 10 : params.getInteger("pageSize"));
        PageHelper.startPage((Integer)paramMap.get("pageNumber"), (Integer)paramMap.get("pageSize"));
        List<Device>deviceList = deviceService.selectAllDevice(paramMap);

        PageInfo<Device> pageInfo = new PageInfo<>(deviceList);

        return ResultModeHelper.succeedPage(pageInfo.getList(), pageInfo.getTotal());
    }
}
