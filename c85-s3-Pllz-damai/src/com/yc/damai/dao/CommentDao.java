package com.yc.damai.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmComment;
import com.yc.damai.util.DBHelper;

/**
 * 表 dm_comment
 * @author 李玲芝
 *
 */
public class CommentDao {
	DBHelper dbh=new DBHelper();
	
	/**
	 * 根据商品id查询该商品的评价
	 * @param pid
	 * @return
	 */
	public List<Map<String,Object>> queryByPid(String pid) {
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	(\n" +
				"		SELECT\n" +
				"			c.id,\n" +
				"			c.oiid,\n" +
				"			c.image1,\n" +
				"			c.image2,\n" +
				"			c.image3,\n" +
				"			c.image4,\n" +
				"			c.createtime,\n" +
				"			c.anonymous,\n" +
				"			c.`comment`,\n" +
				"			u.ename,\n" +
				"			u.id uid,\n" +
				"			u.image uimage,\n" +
				"			p.pname,\n" +
				"			p.id pid,\n" +
				"			p.image pimage\n" +
				"		FROM\n" +
				"			dm_comment c\n" +
				"		INNER JOIN dm_user u ON c.uid = u.id\n" +
				"		INNER JOIN dm_orderitem o ON c.oiid = o.id\n" +
				"		INNER JOIN dm_product p ON o.pid = p.id\n" +
				"		WHERE\n" +
				"			p.id = ?\n" +
				"	) a";
		System.out.println(dbh.query(sql, pid));
		return dbh.query(sql, pid);
	}
	
	public BigDecimal queryLevel(String pid) {
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	(\n" +
				"		SELECT\n" +
				"			TRUNCATE(avg(c.`level`),1) level\n" +
				"		FROM\n" +
				"			dm_comment c\n" +
				"		INNER JOIN dm_orderitem o ON c.oiid = o.id\n" +
				"		INNER JOIN dm_product p ON o.pid = p.id\n" +
				"		WHERE\n" +
				"			p.id = ?\n" +
				"	) a";
		return (BigDecimal) dbh.query(sql, pid).get(0).get("level");
	}
	
	/**
	 * 插入评论
	 * @param dc DmComment 评论的实体类
	 * @return
	 */
	public int insert(DmComment dc) {
		String sql="INSERT INTO dm_comment (\n" +
				"	id,\n" +
				"	uid,\n" +
				"	oiid,\n" +
				"	COMMENT,\n" +
				"	image1,\n" +
				"	image2,\n" +
				"	image3,\n" +
				"	image4,\n" +
				"	anonymous,\n" +
				"	createtime,\n" +
				"   level\n"+
				")\n" +
				"VALUES\n" +
				"	(null, ?, ?, ?, ?, ?, ?, ?,?, now(),?)";
		
		return dbh.update(sql, 
				dc.getUid(),
				dc.getOiid(),
				dc.getComment(),
				dc.getImage1(),
				dc.getImage2(),
				dc.getImage3(),
				dc.getImage4(),
				dc.getAnonymous(),
				dc.getLevel()
				);
	}
	
	/**
	 * 评论的个数
	 * @param oiid 订单详情id
	 * @return
	 */
	public int countCmt(String oiid) {
		String sql="select * from dm_comment where oiid=?";
		return dbh.count(sql, oiid);
	}
	
}
