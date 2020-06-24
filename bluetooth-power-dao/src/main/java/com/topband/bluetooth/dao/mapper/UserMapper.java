package com.topband.bluetooth.dao.mapper;

import com.topband.bluetooth.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper{
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
