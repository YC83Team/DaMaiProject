package com.yc.damai.web;

import com.yc.damai.dao.BkOrdersDao;
import com.yc.damai.dao.OrdersDao;
import com.yc.damai.po.Result;
import com.yc.damai.util.AlipayUtil;
import com.yc.damai.util.SendSms;
import com.yc.damai.util.sendEmail;
import org.apache.commons.mail.EmailException;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/pay.do")
public class PayServlet extends BaseServlet {

    /*
    支付接入
     */
    protected void pay(HttpServletRequest request, HttpServletResponse response) throws IOException, EmailException {
        String oid=request.getParameter("oid");
        String sum=request.getParameter("sum");
        String outTradeNo = UUID.randomUUID().toString();
        String result = AlipayUtil.createWebPay(outTradeNo,sum,"123");
        new OrdersDao().alreadyPay(oid);   //修改订单状态为已支付
        new BkOrdersDao().alreadyPay(oid);   //修改订单状态为已支付
        response.setContentType("text/html;charset=utf8");
        response.getWriter().write(result);
        response.getWriter().flush();
        response.getWriter().close();


    }

}
