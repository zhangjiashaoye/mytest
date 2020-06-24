package com.topband.bluetooth.common.util;

import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
public class IdGen {

	public  static String LAST_CLASSIFY_ID = "";
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
	}

}
