package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.yc.damai.dao.ProductDao;
import com.yc.damai.po.DmProduct;
import com.yc.damai.po.Result;

import redis.clients.jedis.Jedis;

@WebServlet("/product.do")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询热门商品
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryHotProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Map<String, Object>> list = new ProductDao().queryHotProduct();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		print(response, map);
	}
	
	/**
	 * 查询后台管理的商品
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void queryByBack(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		
		/**
		 * dp  要装载的实体对象
		 * properties 存放属性值的 map 集合
		 */
		DmProduct dp=new DmProduct();
		//装载方法
		BeanUtils.populate(dp, request.getParameterMap());
		
		List<Map<String, Object>> list = new ProductDao().queryByBack(dp,page,rows);
		int total=new ProductDao().countPage(dp);
		
		Map<String, Object> map = new HashMap<String, Object>();
		//easyui必须返回rows  total两个数据才能进行分页
		map.put("rows", list);
		map.put("total", total);
		
		print(response, map);
	}

	/**
	 * 查询最新商品
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryNewProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Map<String, Object>> list = new ProductDao().queryNewProduct();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		print(response, map);
	}

	/**
	 * 分类查询
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryByList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//当参数为空时
		if (isParamterEmpty(request, "pageIndex") || isParamterEmpty(request, "pageSize")
				|| isParamterEmpty(request, "category")) {
			return ;
		}

		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize")); 
		int category = Integer.parseInt(request.getParameter("category"));
		
		pageSize=pageSize>0?pageSize:5; // 默认每一页大小为5
		pageIndex=pageIndex>0?pageIndex:1;
		category=category>0?category:1;

		System.out.println("pageIndex=" + pageIndex);

		ProductDao pdao=new ProductDao();
		List<Map<String, Object>> list = pdao.queryByListByPage(pageIndex, pageSize, category);//商品信息
		int pageCount =pdao.countPage(pageSize, category);//总共的页数
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pageCount",pageCount);
		
		System.out.println(list);
		print(response, map);
	}
	
	/**
	 * 查询商品详情
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryByDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (isParamterEmpty(request, "id") ) {
			return ;
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		List<Map<String, Object>> list=new ProductDao().queryByDetail(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		
		System.out.println(list);
		print(response, map);
		
	}
	
	//保存商品
	protected void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		
		DmProduct dp=new DmProduct();
		//装载方法
		BeanUtils.populate(dp, request.getParameterMap());
		//商品名称验证    长度判断，空值验证
		if(dp.getPname()==null || dp.getPname().trim().isEmpty()) {
			print(response,new Result(0,"商品名称不能为空！"));
			return;
		}
		//价格验证
		if(dp.getShopPrice()==null || dp.getShopPrice()<=0) {
			print(response,new Result(0,"商品价格必须大于0！"));
			return;
		}
		
		// id 为空或者等于0 是新增
		if(dp.getId() == null || dp.getId() == 0) {
			new ProductDao().insert(dp);
			print( response, new Result(1,"商品添加成功!"));
		} else {
			new ProductDao().update(dp);
			print( response, new Result(1,"商品修改成功!"));
		}
		
	}
	
	//删除商品
	protected void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (isParamterEmpty(request, "id") ) {
			return ;
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		if(new ProductDao().delete(id)==1) {
			print( response, new Result(1,"商品删除成功!"));
			return;
		}
		print( response, new Result(0,"商品删除失败!"));
	}

	/**
	 * 判断参数是否为空
	 * 
	 * @param request       请求对象
	 * @param parameterName 参数名
	 * @return
	 */
	protected boolean isParamterEmpty(HttpServletRequest request, String parameterName) {
		if (request.getParameter(parameterName) == null)
		{
			return true;
		}
		return false;
	}
	
	 /*
    查询单个商品
     */
    protected void querySingleProduct(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        Jedis jedis =new Jedis();
        String id =request.getParameter("id");
        //todo 将浏览记录保存到redis
        long cnt=jedis.incr("product"+id);
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        if (user==null){
            List<Map<String,Object>> list=new ProductDao().querySingle(id);
            list.get(0).put("cnt",cnt);
            print(response,list.get(0));
        }else{
            String uid = "" + user.get("id");
            jedis.sadd("step"+uid,id);
            jedis.expire(uid,604800);//604800七天
            List<Map<String,Object>> list=new ProductDao().querySingle(id);
            list.get(0).put("cnt",cnt);
            print(response,list.get(0));
        }
        jedis.close();
    }
	
	/*
    查询是都已收藏
     */
    protected void queryIsCollect(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        String uid = "" + user.get("id");
        String id =request.getParameter("id");
        Jedis jedis =new Jedis();
        Boolean isCollect =jedis.sismember("collection"+uid,id);
        if (isCollect){
            print(response,new Result<>(1,"已收藏"));
        }else{
            print(response,new Result<>(0,"未收藏"));
        }
        jedis.close();
    }

    /*
    收藏商品
     */
    protected void collection(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        Jedis jedis =new Jedis();
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        if (user==null){
            print(response,new Result<>(0,"请先登录"));
        }else{
            String uid = "" + user.get("id");
            String id =request.getParameter("id");
            jedis.sadd("collection"+uid,id);//把id存到用户里
            if (!(id == null) || !id.trim().isEmpty()){
                print(response,new Result<>(1,"收藏成功"));
                Set<String> set=jedis.smembers("collection"+uid);
                System.out.println(set);
            }else {
                print(response,new Result<>(0,"很遗憾 收藏失败请联系客服"));
            }
        }

        jedis.close();

    }
    /*
    取消收藏
     */
    protected void remCollection(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
        String uid = "" + user.get("id");
        String id =request.getParameter("id");
        Jedis jedis =new Jedis();
        jedis.srem("collection"+uid,id);
        if (!(id == null) || !id.trim().isEmpty()){
            print(response,new Result<>(1,"取消收藏成功"));
            Set<String> set=jedis.smembers("collection"+uid);
            System.out.println(set);
        }else {
            print(response,new Result<>(0,"很遗憾 无法取消请联系客服"));
        }
        jedis.close();

    }
    
}
