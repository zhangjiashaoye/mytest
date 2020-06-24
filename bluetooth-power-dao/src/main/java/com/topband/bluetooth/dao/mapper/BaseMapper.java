package com.topband.bluetooth.dao.mapper;

public interface BaseMapper<T, F> {
	int deleteByPrimaryKey(F id);

	int insert(T record);

	T selectByPrimaryKey(F id);

	int updateByPrimaryKey(T record);
	
	int insertSelective(T F);
	
	int updateByPrimaryKeySelective(T F);


}
