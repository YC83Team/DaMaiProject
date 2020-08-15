package com.yc.damai.dao;

import com.yc.damai.po.DmAdminuser;
import com.yc.damai.util.DBHelper;

import java.util.List;
import java.util.Map;

public class AdminDao {
    DBHelper dbHelper =new DBHelper();
    /*
    管理员登录
    */
    public List<Map<String, Object>> selectByLogin(DmAdminuser da){
        String sql ="select * from dm_adminuser where username=?  and password=?";
        return dbHelper.query(sql, da.getUsername(), da.getPassword());
    }

}
