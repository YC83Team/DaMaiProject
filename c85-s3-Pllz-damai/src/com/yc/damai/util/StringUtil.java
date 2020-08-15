package com.yc.damai.util;

public class StringUtil {
	
	/**
	 * 转字符串为为int
	 * @param str
	 * @return
	 */
	static public Integer parseInt(String str){
		if(str==null || str.trim().equals("")) {
			return 0;
		}
		return Integer.parseInt(str);
	}
	
	static public Integer parseInt(double dbl){
		int in = new Double(dbl).intValue();
		System.out.println("in:"+in);
		return in;
	}
	
}
