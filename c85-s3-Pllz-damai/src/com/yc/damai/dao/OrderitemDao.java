package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import com.yc.damai.po.TmpOrder;
import com.yc.damai.util.DBHelper;

public class OrderitemDao {


	/**

	 * 	添加订单明细记录

	 * @param uid

	 */
	
	private DBHelper dbh=new DBHelper();

	public int insert(String uid) {

		// -- LAST_INSERT_ID() 获取最近产生的主键值( 自增列 )

		String sql = "INSERT INTO dm_orderitem SELECT\n" +

				"	NULL,\n" +

				"	a.count,\n" +

				"	a.count * b.shop_price,\n" +

				"	a.pid,\n" +

				// 使用子查询获取到当前新增的订单id

				"	(select max(id) from dm_orders )\n" + 

				"FROM\n" +

				"	dm_cart a\n" +

				"JOIN dm_product b ON a.pid = b.id\n" +

				"WHERE\n" +

				"	uid = ?";

		return new DBHelper().update(sql, uid);

	}

	/**

	 * 	添加订单明细记录
	 * 自己选择的商品

	 * @param uid

	 */
	public int insert02(TmpOrder[] tmp) {
		
		StringBuilder sb=new StringBuilder("");
		List< Object> pramas=new ArrayList< Object>();
		
		for(TmpOrder t:tmp) {
			sb.append(
						"	SELECT\n" +
						"			p.shop_price,\n" +
						"			? count,\n" +
						"			p.id pid,\n" +
						"			p.shop_price * ? item\n" +
						"		FROM\n" +
						"			dm_product p\n" +
						"		WHERE\n" +
						"			p.id = ?\n" +
						"		UNION\n" 
					);
			
			pramas.add(t.getCount());
			pramas.add(t.getCount());
			pramas.add(t.getPid());
					
		}

		String sbStr=sb.toString().substring(0, sb.length()-6);

		String sql="INSERT INTO dm_orderitem SELECT\n" +
				"	NULL,\n" +
				"	count,\n" +
				"	sum(item) total,\n" +
				"	pid,\n" +
				"	(SELECT max(id) FROM dm_orders) oid\n" +
				"FROM\n" +
				"	(\n" +
				sbStr+
				"	) t\n" +
				"GROUP BY\n" +
				"	t.pid";
		
		

		return dbh.update(sql, pramas.toArray());
	}
	

	public List<?> queryByOid(String oid) {

		String sql = "select * from dm_orderitem a join dm_product b on a.pid=b.id"

				+ " where oid=?";

		return new DBHelper().query(sql, oid);

	}

	public List<Map<String, Object>> queryOiByOid(String oid){
		String sql="select * from dm_orderitem where oid=?";
		return new DBHelper().query(sql, oid);
	}

}