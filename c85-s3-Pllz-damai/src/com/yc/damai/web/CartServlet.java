package com.yc.damai.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.CartDao;
import com.yc.damai.dao.UserDao;


@WebServlet("/cart.do")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    private CartDao cdao=new CartDao();
    
    private UserDao udao = new UserDao();
	
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid= udao.queryId((Map<String, Object>)request.getSession().getAttribute("loginedUser")) + "";  
		String pid=request.getParameter("pid");
		String quantity=request.getParameter("quantity");
		
		if(cdao.update(uid, pid,quantity)==0) {
			//结果为0，说明该用户还是没有添加过商品
			cdao.insert(uid, pid,quantity);
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("msg", "购物车添加成功");
		print(response, map);
		//response.getWriter().append("{\"msg\":\"购物车添加成功!\"}");
		
	}

	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid= udao.queryId((Map<String, Object>)request.getSession().getAttribute("loginedUser")) + "";  
		List<?> list=cdao.queryByUid(uid);
		print(response, list);
		
	}
}
