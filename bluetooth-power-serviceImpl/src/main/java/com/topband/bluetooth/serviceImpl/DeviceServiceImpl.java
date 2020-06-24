package com.topband.bluetooth.serviceImpl;

import com.topband.bluetooth.dao.mapper.DeviceMapper;
import com.topband.bluetooth.entity.Device;
import com.topband.bluetooth.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;


    @Override
    public List<Device> selectAllDevice(Map<String, Object> paramMap) {
        return deviceMapper.selectAllDevice(paramMap);
    }
}
