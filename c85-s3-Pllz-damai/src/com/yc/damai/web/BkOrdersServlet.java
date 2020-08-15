package com.yc.damai.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.yc.damai.dao.BkOrdersDao;
import com.yc.damai.dao.ProductDao;
import com.yc.damai.po.DmProduct;
import com.yc.damai.po.Result;


@WebServlet("/bkorder.do")
public class BkOrdersServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private BkOrdersDao bkdao=new BkOrdersDao();
       
    /**
     * 查询所有的后台订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		String oid=request.getParameter("id");
		String name=request.getParameter("name");
		
		List<Map<String, Object>> list = bkdao.query(page,rows,oid,name);
		int total=bkdao.countPage(oid,name);
		
		Map<String, Object> map = new HashMap<String, Object>();
		//easyui必须返回rows  total两个数据才能进行分页
		map.put("rows", list);
		map.put("total", total);
		
		print(response, map);
	}
	
	/**
	 * 发货
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void sendPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String oid=request.getParameter("oid");
		System.out.println("oid:"+oid);
		int result=bkdao.sendPro(oid);
		if(result==0) {
			print(response, new Result(0,"发货失败"));
		}else {
			print(response, new Result(1,"发货成功"));
		}
		
	}
	
	//保存修改的内容
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String addr=request.getParameter("addr");
			String phone=request.getParameter("phone");
			
			System.out.println("id:"+id);
			System.out.println("name:"+name);
			
			int result=bkdao.save(id,name,addr,phone);
			if(result==0) {
				print(response, new Result(0,"保存失败"));
			}else {
				print(response, new Result(1,"保存成功"));
			}
			
		}

}
