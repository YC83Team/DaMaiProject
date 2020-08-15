package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmProduct;
import com.yc.damai.util.DBHelper;

/**
 * 给商家使用的订单表
 * @author 李玲芝
 *
 */
public class BkOrdersDao {
	DBHelper dbh=new DBHelper();
	
	/**
	 * 把订单记录插入到后台订单记录
	 * @param oid
	 */
	public void insert(String oid) {
		String sql="INSERT INTO dm_bkorders SELECT\n" +
				"	o.id,\n" +
				"	o.total,\n" +
				"	o.createtime,\n" +
				"	o.state,\n" +
				"	o.uid,\n" +
				"	o.aid,\n" +
				"	a.addr,\n" +
				"	a.phone,\n" +
				"	a.`name`\n" +
				"FROM\n" +
				"	dm_orders o\n" +
				"INNER JOIN dm_address a ON o.aid = a.id\n" +
				"WHERE\n" +
				"	o.id NOT IN (SELECT id FROM dm_bkorders)\n" ;
		
		List<Object> params=new ArrayList<Object>();
		
		if(oid!=null && !oid.trim().isEmpty()) {
			sql=sql+"  and o.id=?";
			params.add(oid);
		}
		new DBHelper().update(sql,params.toArray());
	}
	
	/**
	 * 查询所有的后台订单记录
	 * @return
	 */
	public List<Map<String,Object>> query(String page,String rows,String oid,String name){
		
		StringBuilder where=new StringBuilder("where 1=1 ");
		List<Object> params=new ArrayList<Object>();
		
		if(name!=null && name.trim().isEmpty()==false) {
			where.append(" and name like ?");
			params.add("%"+name+"%");
		}
		if(oid!=null && oid.trim().isEmpty()==false) {
			where.append(" and id=?");
			params.add(oid);
		}
		String sql="SELECT\n" +
				"	id,\n" +
				"	total,\n" +
				"	createtime,\n" +
				"	state,\n" +
				"	uid,\n" +
				"	aid,\n" +
				"	addr,\n" +
				"	phone,\n" +
				"	name\n" +
				"FROM\n" +
				"	dm_bkorders\n" +
				where+
				"  limit ?,?";
		
		int ipage=Integer.parseInt(page);
		int irows=Integer.parseInt(rows);
		ipage=(ipage-1)*irows;
		params.add(ipage);
		params.add(irows);
		
		return new DBHelper().query(sql,params.toArray());
	}
	
	/**
	 * 查询商品的记录数
	 * @param rows 
	 * @param page 
	 * @return
	 */
	public int countPage(String oid,String name){
		
		StringBuilder where=new StringBuilder("");
		List<Object> params=new ArrayList<Object>();
		
		if(name!=null && name.trim().isEmpty()==false) {
			where.append(" and name like ?");
			params.add("%"+name+"%");
		}
		if(oid!=null && oid.trim().isEmpty()==false) {
			where.append(" and id=?");
			params.add(oid);
		}
		
		
		String sql="select * from dm_bkorders where 1=1 "+where.toString();
		return new DBHelper().count(sql,params.toArray());
	}
	
	/**
	 * 发货  
	 * 更改后台订单表和订单表的状态
	 * @return
	 */
	public int sendPro(String oid) {
		String sql01="update dm_bkorders set state=2 where id=?";
		String sql02="update dm_orders set state=2 where id=?";
		
		return dbh.update(sql01,oid) & dbh.update(sql02,oid);
		
	}
	
	
	/**
	 * 保存修改的内容
	 * @param oid
	 * @return
	 */
	public int save(String oid,String name,String addr,String phone) {
		
		String sql=" update dm_bkorders set name=?,phone=?,addr=?"+
					" where id=?";
		List<Object> params=new ArrayList<Object>();
		
		params.add(name);
		params.add(phone);
		params.add(addr);
		params.add(oid);
		
		return new DBHelper().update(sql,params.toArray());
	}
	
	/**
	 * 支付成功 修改订单状态为已支付
	 * @param oid
	 * @return
	 */
	public int  alreadyPay(String oid){
		String sql="update dm_bkorders set state=1 where id=?";
		return new DBHelper().update(sql, oid);
	}

}
