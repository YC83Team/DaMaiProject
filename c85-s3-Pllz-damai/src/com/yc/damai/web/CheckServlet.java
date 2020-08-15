package com.yc.damai.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;

import com.yc.damai.po.Result;
import com.yc.damai.util.SendSms;
import com.yc.damai.util.sendEmail;

import redis.clients.jedis.Jedis;

@WebServlet("/check.do")
public class CheckServlet extends BaseServlet {
	 sendEmail sendEmail=new sendEmail();
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request,response);
	    }
	    /*
	    获取邮箱验证码
	     */
	    protected void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException, EmailException, ServletException {
	        String address =request.getParameter("email");
	        String code = (int)(Math.random()*1000000)+"";
	        if (address==null || address.trim().isEmpty()){
	            print(response,new Result<>(0,"请填写邮箱地址"));
	        }else if (code != null){
	            request.getSession().setAttribute("Code",code);//todo 将生成的验证码存到会话中
	            sendEmail.send(address,code);
	            print(response,new Result<>(1,"发送成功"));
	        }
	    }
	    /*
	    获取手机
	     */
	    protected void getPhoneCode(HttpServletRequest request, HttpServletResponse response) throws IOException, EmailException, ServletException {
	        String phone =request.getParameter("phone");
	        String code = (int)(Math.random()*1000000)+"";//todo 验证码生成
	        Jedis jedis= new Jedis();

	        if (phone==null || phone.trim().isEmpty()){
	            print(response,new Result<>(0,"请填写手机号码"));
	        }else{
	            SendSms.send(phone,code);
	            print(response,new Result<>(1,"发送成功"));
	            jedis.setex(phone, 180, code);   //todo 缓存验证码到redis并设置超时时间
	            jedis.close();
	        }
	    }

}
