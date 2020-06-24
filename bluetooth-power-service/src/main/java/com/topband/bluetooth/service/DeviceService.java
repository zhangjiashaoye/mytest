package com.topband.bluetooth.service;

import com.topband.bluetooth.entity.Device;

import java.util.List;
import java.util.Map;

public interface DeviceService {

    List<Device> selectAllDevice(Map<String, Object> paramMap);
}
