package com.yc.damai.dao;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.fabric.xmlrpc.base.Params;
import com.yc.damai.po.TmpOrder;
import com.yc.damai.util.DBHelper;



public class OrdersDao {

	private DBHelper dbh=new DBHelper();

	/**
	 * 添加订单主表记录 orders
	 * 添加购物车所有的商品
	 * @param uid
	 * @return
	 */

	public int insert(String uid) {

		String sql = "INSERT INTO dm_orders SELECT\n" + "	NULL,\n" + "	c.total,\n" + "	now(),\n" + "	0,\n"

				+ "	a.id,\n" + "	b.id\n" + "FROM\n" + "	dm_user a\n" +

				// 获取默认地址

				"JOIN dm_address b ON a.id = b.uid\n" + "AND dft = 1\n" + "JOIN (\n" +

				// 求订单总金额

				"	SELECT\n" + "		sum(b.shop_price * a.count) total,\n" + "		a.uid\n" + "	FROM\n"

				+ "		dm_cart a\n" + "	JOIN dm_product b ON a.pid = b.id\n" + "	WHERE\n" + "		a.uid = ?\n"

				+ "	GROUP BY\n" + "		a.uid\n" + ") c ON a.id = c.uid\n" + "WHERE\n" + "	a.id = ?";

		return new DBHelper().update(sql, uid, uid);

	}
	
	/**
	 * 添加订单主表记录 orders
	 * 自己选择的商品
	 * @param tmp
	 * @return  1 添加成功   0 添加失败
	 */
	public int insert02(TmpOrder[] tmp) {
		
		StringBuilder sb=new StringBuilder("");
		List< Object> pramas=new ArrayList< Object>();
		
		for(TmpOrder t:tmp) {
			sb.append(
					"		SELECT\n" +
					"			p.shop_price,\n" +
					"			?,\n" +
					"			p.id pid,\n" +
					"			p.shop_price * ? item\n" +
					"		FROM\n" +
					"			dm_product p\n" +
					"		WHERE\n" +
					"			p.id = ?\n" +
					"         union\n"
					);
			
			pramas.add(t.getCount());
			pramas.add(t.getCount());
			pramas.add(t.getPid());
					
		}

		String sbStr=sb.toString().substring(0, sb.length()-6);
		
		
		String sql= "INSERT INTO dm_orders SELECT\n" +
				"	NULL,\n" +
				"	sum(item) total,\n" +
				"	NOW(),\n" +
				"	0,\n" +
				"	u.id uid,\n" +
				"	a.id aid\n" +
				"FROM\n" +
				"	(\n" +
				sbStr+
				"	) t,\n" +
				"	dm_address a\n" +
				"JOIN dm_user u ON a.uid = u.id\n" +
				"AND a.dft = 1\n" +
				"WHERE\n" +
				"	u.id = ?\n" +
				"GROUP BY\n" +
				"	u.id";
		
		pramas.add(tmp[0].getUid());

		return dbh.update(sql, pramas.toArray());
	}



	public Map<String, Object> queryNewOrders(String uid) {

		String sql = "select a.*,b.addr, b.phone, b.name from dm_orders a join dm_address b on a.aid=b.id"

				+ " where a.uid=? and state=0 order by id desc limit 0,1";

		List<Map<String, Object>> list = new DBHelper().query(sql, uid);

		if (list.isEmpty()) {

			return null;

		} else {

			return list.get(0);

		}

	}

	/**
	 * 查询指定用户的订单
	 * @param uid
	 * @return
	 */
	public List<Map<String, Object>>  queryByUid(String uid){
		String sql="select * from(SELECT\n" +
				"	o.id,\n" +
				"	o.total,\n" +
				"	o.state,\n" +
				"	o.uid,\n" +
				"	o.createtime,\n" +
				"	oi.pid,\n" +
				"oi.id oid,\n"+
				"	oi.count,\n" +
				"	oi.total / oi.count price,\n" +
				"  oi.total itemtotal,\n" +
				"	p.pname,\n" +
				"	p.image\n" +
				"FROM\n" +
				"	dm_orders o\n" +
				"INNER JOIN dm_orderitem oi ON o.id = oi.oid\n" +
				"JOIN dm_product p ON oi.pid = p.id\n" +
				"WHERE\n" +
				"	o.uid = ?\n" +
				"ORDER BY\n" +
				"	o.createtime DESC) a";
		return new DBHelper().query(sql, uid);
	}
	
	/**
	 * 根据订单编号查询订单
	 * @param oid
	 * @return
	 */
	public Map<String, Object>  queryOrder(String oid){
		
		String sql="select a.*,b.addr, b.phone, b.name from dm_orders a join dm_address b on a.aid=b.id"
				+ " where a.id=? and state=0 order by id desc limit 0,1";
		return new DBHelper().query(sql, oid).get(0);
	}
	
	/**
	 * 支付成功 修改订单状态为已支付
	 * @param oid
	 * @return
	 */
	public int  alreadyPay(String oid){
		String sql="update dm_orders set state=1 where id=?";
		return new DBHelper().update(sql, oid);
	}

	public static void main(String[] args) {

		// 这种写法有数据库事务的问题

		new OrdersDao().insert("2");

		// 出现异常, 会导致 订单被创建, 而订单明细没有创建, 购物车没有被清空

		new OrderitemDao().insert("2");

		new CartDao().deleteByUid("2");

	}
	
	public int mksGetPro(String id) {
		String sql01="update dm_bkorders set state=3 where id=?";
		String sql02="update dm_orders set state=3 where id=?";
		
		return new DBHelper().update(sql01,id) & new DBHelper().update(sql02,id);
		
	}
	
	/**
	 * 查询当前用户的最新id
	 * @return
	 */
	public int selectLatestId() {
		String sql="select * from (select max(id) id from dm_orders)a ";
		return (int) new DBHelper().query(sql).get(0).get("id");
	}
}