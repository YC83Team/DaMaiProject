package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;

import com.yc.damai.po.TmpOrder;
import com.yc.damai.util.DBHelper;

public class CartDao {
	
	/**
	 * 给某用户添加新的购物车商品
	 * @param uid 
	 * @param pid
	 * @return 新增的记录数
	 */
	public int insert(String uid,String pid,String quantity) {
		String sql="insert into dm_cart values(null,?,?,?)";
		return new DBHelper().update(sql, uid,pid,quantity);
	}
	

	/**
	 * 改变某个用户购物车商品数量
	 * @param uid 
	 * @param pid
	 * @return 更新的记录数
	 */
	public int update(String uid,String pid,String quantity) {
		String sql="update dm_cart set count=count+? where uid=? and pid=?";
		return new DBHelper().update(sql,quantity, uid,pid);
	}
	
	/**
	 * 查询指定用户的购物车信息
	 * @param uid
	 * @return
	 */
	public List<?> queryByUid(String uid){
		String sql="select * from dm_cart a join dm_product b on a.pid=b.id where a.uid=?";
		return new DBHelper().query(sql, uid);
	}
	
	/**
	 * 删除指定用户的购物车信息
	 * @param uid
	 * @return
	 */
	public int deleteByUid(String uid){
		String sql="delete  from dm_cart where uid=?";
		return new DBHelper().update(sql, uid);
	}
	
	/**
	 * 删除指定用户的购物车信息
	 * 只删除用户自己选择的商品
	 * @param uid
	 * @return
	 */
	public int deleteByUid02(TmpOrder[] tmp){
		StringBuilder sb=new StringBuilder("delete  from dm_cart where 1=1");
		List< Object> pramas=new ArrayList< Object>();
		
		
		
		for(TmpOrder t:tmp) {
			sb.append(" or pid=? ");
			pramas.add(t.getPid());
		}
		
		sb.append(" and uid=?");
		pramas.add(tmp[0].getUid());
		
		
		return new DBHelper().update(sb.toString(), pramas.toArray());
	}
}
