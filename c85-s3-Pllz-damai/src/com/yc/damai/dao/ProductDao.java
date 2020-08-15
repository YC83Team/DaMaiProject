package com.yc.damai.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmProduct;
import com.yc.damai.util.DBHelper;

public class ProductDao {
	
	/**
	 * 查询热门商品
	 * @return
	 */
	public List<Map<String, Object>> queryHotProduct(){

		String sql = "select * from dm_product where is_hot=1 limit 0,10";
		return new DBHelper().query(sql);
	}
	
	/**
	 * 查询商品  后台管理
	 * @param rows 
	 * @param page 
	 * @return
	 */
	public List<Map<String, Object>> queryByBack(DmProduct dp,String page, String rows){
		StringBuilder where=new StringBuilder("");
		List<Object> params=new ArrayList<Object>();
		
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty()==false) {
			where.append(" and pname like ?");
			params.add("%"+dp.getPname()+"%");
		}
		if(dp.getCid()!=null && dp.getCid()!=0) {
			where.append(" and cid=?");
			params.add(dp.getCid());
		}
		if(dp.getIsHot()!=null && dp.getIsHot()!=0) {
			where.append(" and is_hot=?");
			params.add(dp.getIsHot());
		}
		
		
		int ipage=Integer.parseInt(page);
		int irows=Integer.parseInt(rows);
		ipage=(ipage-1)*irows;
		String sql="SELECT\n" +
				"	a.*, b.cname\n" +
				"FROM\n" +
				"	dm_product a\n" +
				"JOIN dm_category b ON a.cid = b.id\n"
				+"where 1=1\n"
				+where.toString()+"\n"
				+" limit ?,?";
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
	public int countPage(DmProduct dp){
		
		StringBuilder where=new StringBuilder("");
		List<Object> params=new ArrayList<Object>();
		
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty()==false) {
			where.append(" and pname like ?");
			params.add("%"+dp.getPname()+"%");
		}
		if(dp.getCid()!=null && dp.getCid()!=0) {
			where.append(" and cid=?");
			params.add(dp.getCid());
		}
		if(dp.getIsHot()!=null && dp.getIsHot()!=0) {
			where.append(" and is_hot=?");
			params.add(dp.getIsHot());
		}
		
		String sql="select * from dm_product where 1=1 "+where.toString();
		return new DBHelper().count(sql,params.toArray());
	}
	
	/**
	 * 查询最新商品
	 * @return
	 */
	public List<Map<String, Object>> queryNewProduct(){

		String sql="select * from dm_product ORDER BY createtime desc limit 0,10";
		return new DBHelper().query(sql);
	}
	
	/**
	 * 计算商品类别下共有多少页数据
	 * @param pageSize  每页的大小
	 * @param category  商品类别id
	 * @return pageCount  总共的页数
	 */
	public int countPage(int pageSize,int category) {
		
		int total=queryByListByAll(category).size(); //一共多少条数据
		int pageCount=0; //一共多少页
		
		if(total%2==0) {
			pageCount=total/pageSize;
		}else {
			pageCount=total/pageSize+1;
		}
		
		return pageCount!=0?pageCount:1;
	}
	
	/**
	 * 查询每个类别的所有商品
	 * @param category  商品的类别id
	 * @return
	 */
	public List<Map<String, Object>> queryByListByAll(int category){

		if(category<1) {
			return null;
		}
		
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	dm_product p\n" +
				"INNER JOIN dm_category c ON p.cid = c.id\n" +
				"WHERE\n" +
				"	c.id = ?";
		
		return new DBHelper().query(sql,category);
	}
	
	/**
	 * 分页查询商品
	 * @param pageIndex 页码
	 * @param pageSize  每一页的大小
	 * @param category  商品的类别id
	 * @return
	 */
	public List<Map<String, Object>> queryByListByPage(int pageIndex,int pageSize,int category){

		if(pageIndex<1 || pageSize<1 || category<1) {
			return null;
		}
		
		String sql="SELECT\n" +
				"	p.pname,\n" +
				"	p.id,\n" +
				"	p.market_price,\n" +
				"	p.shop_price,\n" +
				"	p.image,\n" +
				"	p.pdesc,\n" +
				"	p.is_hot,\n" +
				"	p.createtime,p.cid,\n" +
				"	c.cname\n" +
				"FROM\n" +
				"	dm_product p\n" +
				"INNER JOIN dm_category c ON p.cid = c.id\n" +
				"WHERE\n" +
				"	c.id = ?\n" +
				"LIMIT ?,?";
		
		
		int begin=(pageIndex-1)*pageSize;
		
		return new DBHelper().query(sql,category,begin,pageSize);
	}
	
	/**
	 * 查询商品详情
	 * @param id  商品id
	 * @return
	 */
	public List<Map<String, Object>> queryByDetail(int id){

		String sql="select * from dm_product where id=?";
		
		return new DBHelper().query(sql,id);
	}
	
	/**
    	商品详情查询
     */
    public List<Map<String,Object>> querySingle(String id){
        String sql ="select * from dm_product where id=?";
        return new DBHelper().query(sql,id);
    }
	
	/**
	 * 新增商品
	 * @param dp
	 */
	public void insert(DmProduct dp) {
		String sql="insert into dm_product values(null,?,?,?,?,?,?,now(),?)";
		new DBHelper().update(sql, 
				dp.getPname(),
				dp.getMarketPrice(),
				dp.getShopPrice(),
				dp.getImage(),
				dp.getPdesc(),
				dp.getIsHot(),
				dp.getCid());
	}
	
	/**
	 * 修改商品
	 * @param dp
	 */
	public void update(DmProduct dp) {
		String sql = "update dm_product set "
				+ "pname=?,"
				+ "market_price=?,"
				+ "shop_price=?,"
				+ "image=?,"
				+ "pdesc=?,"
				+ "is_hot = ?,"
				+ "cid = ?"
				+ " where id=?";

		new DBHelper().update(sql
				,dp.getPname()
				,dp.getMarketPrice()
				,dp.getShopPrice()
				,dp.getImage()
				,dp.getPdesc()
				,dp.getIsHot()
				,dp.getCid()
				,dp.getId());
	}
	
	/**
	 * 删除商品
	 * @param id
	 */
	public int delete(int id) {
		String sql="delete from dm_product where id=?";
		return new DBHelper().update(sql, id);
	}
	
}
