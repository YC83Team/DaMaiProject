package com.yc.damai.web;

import com.yc.damai.dao.AdminDao;
import com.yc.damai.po.DmAdminuser;
import com.yc.damai.po.DmProduct;
import com.yc.damai.po.Result;
import com.yc.damai.util.SendSms;
import com.yc.damai.util.sendEmail;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.mail.EmailException;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin.do")
public class AdminServlet extends BaseServlet {
    AdminDao dao = new AdminDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    /*
    管理员登陆
     */
    protected void admin(HttpServletRequest request, HttpServletResponse response) throws IOException, EmailException, InvocationTargetException, IllegalAccessException, ServletException {
        String username =request.getParameter("username");
        String password =request.getParameter("password");
          /*
        dp 要装载的实体对象
        properties 存进属性值的map集合
         */
        DmAdminuser da = new DmAdminuser();
        //装载方法
        BeanUtils.populate(da,request.getParameterMap());
        if (da.getUsername()==null||da.getUsername().trim().isEmpty()){
            print(response,new Result<>(0,"请填写用户名"));
        }else if (da.getPassword()==null||da.getPassword().trim().isEmpty()){
            print(response,new Result<>(0,"请填写密码"));
        }else {
            List<?> list =dao.selectByLogin(da);
            request.getSession().setAttribute("adminUser", list);//todo 用户存到session
            print(response,new Result<>(1,"登录成功"));
        }

    }
    /*
    获取用户信息
     */
    protected void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("adminUser");
        if (user == null) {
            print(response, new Result<>(0, "请先登录"));
        } else {
//            print(response, user);
            //todo 更新用户实时信息/
            print(response,user);
        }
    }
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("adminUser");
        if(user!=null){
            request.getSession().removeAttribute("adminUser");
            print(response,new Result<>(1,"退出成功"));
        }else {
            print(response,new Result<>(0,"尚未登陆"));
        }


    }

}
