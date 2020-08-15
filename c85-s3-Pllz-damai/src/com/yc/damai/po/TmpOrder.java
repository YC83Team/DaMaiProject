package com.yc.damai.po;
/**
 * 生成订单表的中间表
 * @author 李玲芝
 *
 */
public class TmpOrder {
	
	private int uid;
	private int pid;
	private int count;
	
	public Integer getUid() {
		
		return uid;
	}
	
	
	public TmpOrder() {
		super();
	}


	public TmpOrder(Integer uid, Integer pid, Integer count) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.count = count;
	}


	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
