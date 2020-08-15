package com.yc.damai.dao;

import com.yc.damai.po.DmAddress;
import com.yc.damai.util.DBHelper;

import java.util.List;
import java.util.Map;

public class AddressDao {
    DBHelper dbHelper =new DBHelper();
    /*
    查看所有地址
     */
    public List<Map<String,Object>> queryAll(String uid){
        String sql ="select * from dm_address where uid=?";
        return dbHelper.query(sql,uid);
    }
    /*
    查询单独地址
     */
    public  List<Map<String,Object>> queryById(String id){
        String sql ="select * from dm_address where id=?";
        return dbHelper.query(sql,id);
    }
    /*
    增加地址
     */
    public void insert(DmAddress da, String uid) {
        String sql = "insert into dm_address values (null,?,?,?,?,0,now())";
        dbHelper.update(sql,
                uid,
                da.getAddress(),
                da.getPhone(),
                da.getName());

    }
    public void insert1(String uid,String address,String phone,String name) {
        String sql = "insert into dm_address values (null,?,?,?,?,0,now())";
        dbHelper.update(sql, uid,address,phone,name);


    }
    /*
    修改地址
     */
    public void updateAddress(String addr,String phone,String name,Integer id){
        String sql="update dm_address set "
                + "addr=?,"
                + "phone=?,"
                + "name=?"
                + "where id =?";
        dbHelper.update(sql,addr,phone,name,id);
    }
    /*
    删除地址
     */
    public void del(DmAddress da){
        String sql ="delete from dm_address where id=?";
        dbHelper.update(sql,da.getId());
    }
    /*
    设置为默认地址
     */
    public void clearDft(String uid){
        String sql="update dm_address set dft=0 where uid=? and dft=1";
        dbHelper.update(sql,uid);
    }
    public void updateDft(String id){
        String sql ="update dm_address set dft=1 where id=?";
        dbHelper.update(sql,id);
    }
    
    /**
     * 是否有默认地址
     * @return
     */
    public boolean haveDefault(String uid) {
    	String sql="select * from dm_address where dft=1 and uid=?";
    	return dbHelper.count(sql, uid)>0?true:false;
    }
}
