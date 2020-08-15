package com.yc.damai.util;

import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.sun.mail.util.MailSSLSocketFactory;


public class sendEmail {

    public void send(String address,String code) throws EmailException {
    	
    	
        HtmlEmail email =new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setCharset("utf-8");
        email.setSubject("验证码");
        email.addTo(address);//目标邮箱地址
        email.setFrom("1330501204@qq.com","大麦商城");//你的邮箱地址
        email.setAuthentication("1330501204@qq.com","hxgvukgwbaivjebj");//你的邮箱地址和你的stmp密码
        email.setTextMsg("测试");
        email.setMsg("大麦商城注册验证码:"+code);//内容最好不要太简单了，不然容易进垃圾邮箱
        email.send();
    }
}
