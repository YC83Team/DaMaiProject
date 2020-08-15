package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/BaseServlet")
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * product.do 商品操作的servlet 增删改查 op=add 添加商品 op=query 查询商品
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求对象的字符集 请求只有post需要设置
		request.setCharacterEncoding("UTF-8");
		// 设置请求对象的字符集
		response.setCharacterEncoding("UTF-8");
		// 设置网页的字符集
		response.setContentType("text/html;charset=utf-8");
		
		
		// 获取要调用的方法名
		String op = request.getParameter("op");
		// 通过反射获取指定方法名的方法
		try {
			Method method = this.getClass().getDeclaredMethod(op, HttpServletRequest.class, HttpServletResponse.class);
			// 设置方法可用
			method.setAccessible(true);
			// 执行方法
			method.invoke(this, request, response);

		} catch (Exception e) {
			// 捕获异常
			e.printStackTrace();
			System.out.println("系统错误");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 返回json数据给页面
	 * 
	 * @param response
	 * @param obj
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void print(HttpServletResponse response, Object obj) throws ServletException, IOException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		System.out.println(gson.toJson(obj));
		response.getWriter().print(gson.toJson(obj));
	}
	
	/**
	 * 返回字符串提示消息给页面
	 * 
	 * @param response
	 * @param obj
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void print(HttpServletResponse response, String str) throws ServletException, IOException {
		response.getWriter().print(new Gson().toJson(str));
	}
	
}
