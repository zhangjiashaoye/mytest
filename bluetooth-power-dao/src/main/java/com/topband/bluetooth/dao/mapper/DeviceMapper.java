package com.topband.bluetooth.dao.mapper;

import com.topband.bluetooth.entity.Device;

import java.util.List;
import java.util.Map;

public interface DeviceMapper extends BaseMapper{
    int deleteByPrimaryKey(String id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    List<Device> selectAllDevice(Map<String, Object> paramMap);
}
