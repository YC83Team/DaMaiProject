package com.yc.damai.dao;

import com.yc.damai.util.DBHelper;

import java.util.List;
import java.util.Map;

public class UserDao {
    DBHelper dbHelper =new DBHelper();
    /*
    登录
     */
    public Map<String,Object> selectByLogin(String uname, String phone, String upass){
        String sql ="select * from dm_user where (ename=? or phone=?) and password=?";
        List<Map<String,Object>> list =dbHelper.query(sql,uname,phone,upass);
        if(list.size()==0){
            return null;
        }else{
            Map<String,Object> user =list.get(0);
            return user;
        }
    }
    
    
    /*
    验证码登录
     */
    public Map<String,Object> loginByPhone(String phone){
        String sql ="select * from dm_user where phone=?";
        List<Map<String,Object>> list =dbHelper.query(sql,phone);
        if(list.size()==0){
            return null;
        }else{
            Map<String,Object> user =list.get(0);
            return user;
        }
    }
    /*
    查询用户名 todo 保持用户名唯一
     */
    public List<Map<String, Object>> selectByName(String uname){
        String sql ="select ename from dm_user where ename=? ";
        return dbHelper.query(sql,uname);
    }
    /*
    查询手机号 todo 保持手机号唯一
     */
    public List<Map<String, Object>> selectByPhone(String phone){
        String sql ="select phone from dm_user where phone=? ";
        return dbHelper.query(sql,phone);
    }
    /*
    注册
     */
    public void register(String ename,String cname,String password,String email,String phone,String sex){
        String sql ="insert into dm_user values(null,?,?,?,?,?,?,1,now(),'images/addpicture.jpg')";
        dbHelper.update(sql,ename,cname,password,email,phone,sex);
    }
    
    /**
     * 查询当前用户的id
     * @param loginedUser
     * @return
     */
    public int queryId(Map<String, Object> loginedUser) {
    	
    	String name=loginedUser.get("ename").toString();
    	String pwd=loginedUser.get("password").toString();
    	String sql="select id from dm_user where ename=? and password=?";
    	return  (int) dbHelper.query(sql,name,pwd).get(0).get("id");
    }
    /*
    更新用户信息
     */
    public void flash(String cname,String sex,String email,String phone,String image,String id){
        String sql ="update dm_user set cname=?,email=?,phone=?,sex=?,image=? where id=?";
        dbHelper.update(sql,cname,email,phone,sex,image,id);
    }
    /*
    修改密码
     */
    public void alterPass(String newPass,String uid){
        String sql ="update dm_user set password=? where id=?";
        dbHelper.update(sql,newPass,uid);
    }
    /*
    修改邮箱
     */
    public void alterEamil(String email,String uid){
        String sql ="update dm_user set email=? where id=?";
        dbHelper.update(sql,email,uid);
    }
    /*
    修改手机号
     */
    public void alterPhone(String phone,String uid){
        String sql ="update dm_user set phone=? where id=?";
        dbHelper.update(sql,phone,uid);
    }
    /*
    找回密码
     */
    public void repass(String newPass,String phone){
        String sql ="update dm_user set password=? where phone=?";
        dbHelper.update(sql,newPass,phone);
    }
}
