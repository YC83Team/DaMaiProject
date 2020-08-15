package com.yc.damai.po;
/**
 * 服务器返回给浏览器的信息
 * @author 李玲芝
 *
 */
public class Result<T>{
	//返回结果  0失败   1成功
	private int code;
	//返回的消息  注册成功！
	private String msg;
	//返回给浏览器的数据，list map  实体对象
	private Object data;
	
	
	public Result() {
		super();
	}
	public Result(int code) {
		super();
		this.code = code;
	}
	public Result(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public Result(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
