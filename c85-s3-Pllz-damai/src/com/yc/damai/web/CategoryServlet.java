package com.yc.damai.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.CategoryDao;

@WebServlet("/category.do")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询分类名称
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryCategoryName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Map<String, Object>> list=new CategoryDao().queryCategoryName();
		print(response, list);
	}
	
	/**
	 * 查询一级分类
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryByFirstCat(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Map<String, Object>> list=new CategoryDao().queryByFirstCat();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		
		print(response, map);
	}

	/**
	 * 查询二级分类
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryBySecondCat(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Map<String, Object>> firstList=new CategoryDao().queryByFirstCat();
		Map<String, Object> map;
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		int fid=1;
		for(Map<String, Object> fl:firstList) {
			fid=(int) fl.get("id");
			List<Map<String, Object>> secondList=new CategoryDao().queryBySecondCat(fid);
			map=new HashMap<String, Object>();
			map.put("firstCat", fl);
			map.put("secondCat", secondList);
			res.add(map);
		}
	
		print(response, res);
	}

}
