package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.yc.damai.dao.CommentDao;
import com.yc.damai.po.DmComment;
import com.yc.damai.po.Result;


@WebServlet("/comment.do")
public class CommentServlet extends BaseServlet {
	
	private CommentDao cdao=new CommentDao();
	
	/**
	 * 通过商品id查询该商品的评论
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid=request.getParameter("pid");
		List<Map<String, Object>>  list=cdao.queryByPid(pid);
		BigDecimal level=cdao.queryLevel(pid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("level", level);
		print(response,map);
	}
	
	/**
	 * 新增评论
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		
		//创建评论的实体类
		DmComment dc=new DmComment();
		//装载参数到实体类中
		BeanUtils.populate(dc, request.getParameterMap());
		System.out.println("dc:"+dc);
		if(cdao.insert(dc)==1) {
			
			print( response, new Result(1,"评价发表成功!"));
		}else {
			print( response, new Result(0,"评价发表失败!"));
		}
		
	}
	
	protected void haveCmt(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		
		String oiid=request.getParameter("oiid");
		
		if(cdao.countCmt(oiid)>0) {
			print( response, new Result(1));
		}else {
			print( response, new Result(0));
		}
		
	}
}
