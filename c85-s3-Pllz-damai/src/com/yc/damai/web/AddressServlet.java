package com.yc.damai.web;

import com.yc.damai.conf.Address;
import com.yc.damai.dao.AddressDao;
import com.yc.damai.dao.AdminDao;
import com.yc.damai.po.DmAddress;
import com.yc.damai.po.DmAdminuser;
import com.yc.damai.po.Result;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.mail.EmailException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/address.do")
public class AddressServlet extends BaseServlet {
    AddressDao dao = new AddressDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    /*
    地址查询
     */
    protected void query(HttpServletRequest request, HttpServletResponse response) throws IOException, EmailException, InvocationTargetException, IllegalAccessException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        String uid=""+user.get("id");
        if (user==null){
            print(response,new Result<>(0,"请先登录"));
        }else {
            List<Map<String,Object>> addr =dao.queryAll(uid);
            print(response,addr);
        }
    }
    /*
    单独地址查询
     */
    protected void querySingle(HttpServletRequest request, HttpServletResponse response) throws IOException, EmailException, InvocationTargetException, IllegalAccessException, ServletException {
        String id= request.getParameter("id");

        if (id==null||id.trim().isEmpty()){
            print(response,new Result<>(0,"地址不明"));
        }else {
            List<Map<String,Object>> addr =dao.queryById(id);
            print(response,new Result<>(1,"",addr.get(0)));
        }
    }
    /*
    添加地址
     */
    protected void add(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        String uid=""+user.get("id");
        String name=request.getParameter("name");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");

        if (user == null) {
            print(response, new Result<>(0, "请先登录"));
        } else if(name==null||name.trim().isEmpty()){
            print(response, new Result<>(0, "姓名"));
        } else if (address==null||address.trim().isEmpty()){
        	System.out.println("addres:"+address.trim());
            print(response, new Result<>(0, "地址"));
        }else if (phone==null||phone.trim().isEmpty()){
            print(response, new Result<>(0, "手机"));
        }else{
            dao.insert1(uid,address,phone,name);
            print(response, new Result<>(1, "添加成功"));

        }
    }
    /*
    修改地址
     */
    protected void alter(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        Map map = request.getParameterMap();
        DmAddress da = new DmAddress();
        BeanUtils.populate(da,map);
        String uid=""+user.get("id");

        if (user == null) {
            print(response, new Result<>(0, "请先登录"));
        } else if(da.getName()==null||da.getName().trim().isEmpty()){
            print(response, new Result<>(0, "姓名"));
        } else if (da.getAddress()==null||da.getAddress().trim().isEmpty()){
            print(response, new Result<>(0, "地址"));
        }else if (da.getPhone()==null||da.getPhone().trim().isEmpty()){
            print(response, new Result<>(0, "手机"));
        }else{
            dao.updateAddress(da.getAddress(),da.getPhone(),da.getName(),da.getId());
            print(response, new Result<>(1, "保存成功"));

        }
    }
    /*
    设置默认地址
     */
    protected void setDefault(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, ServletException {
        String id=request.getParameter("id");
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        String uid=""+user.get("id");
        if (user==null){
            print(response,new Result<>(0,"请登录"));
        }else if (id==null&&id.trim().isEmpty()){
            print(response,new Result<>(0,"未选择地址"));
        }else{
            dao.clearDft(uid);
            dao.updateDft(id);
            print(response,new Result<>(1,"修改成功"));
        }

    }
    /*
    删除地址
     */
    protected void del(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, ServletException {
        DmAddress da = new DmAddress();
        //装载方法
        BeanUtils.populate(da,request.getParameterMap());
        if (da.getId()==null||da.getId().toString().isEmpty()){
            print(response,new Result<>(0,"未选择地址"));
        }else{
            dao.del(da);
            print(response,new Result<>(1,"删除成功"));

        }

    }

    protected void get(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, ServletException {
        print(response,new Result<>(0,"",Address.list));
    }

}
