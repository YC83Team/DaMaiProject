package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;

/**
 * tm_category
 * @author 李玲芝
 *
 */
public class CategoryDao {
	
	/**
	 * 查询分类名称
	 * @return
	 */
	public List<Map<String, Object>> queryCategoryName(){
		String sql="SELECT * from dm_category";
		return new DBHelper().query(sql);
	}
	/**
	 * 查询一级分类
	 * @return
	 */
	public List<Map<String, Object>> queryByFirstCat() {
		
		String sql="SELECt cname,id FROM dm_category WHERE pid is null";
		return new DBHelper().query(sql);
	}
	
	/**
	 * 查询二级分类
	 * @return
	 */
	public List<Map<String, Object>> queryBySecondCat(int pid) {
		
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	(\n" +
				"		SELECT\n" +
				"			a.id secid,\n" +
				"			a.cname seccname,\n" +
				"			b.id fircid,\n" +
				"			b.cname fircname\n" +
				"		FROM\n" +
				"			dm_category a\n" +
				"		INNER JOIN dm_category b ON a.pid = b.id\n" +
				"		WHERE\n" +
				"			a.pid=?\n" +
				"	)a";
		return new DBHelper().query(sql,pid);
	}
}
