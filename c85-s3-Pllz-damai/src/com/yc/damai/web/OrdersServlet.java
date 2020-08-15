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

import com.google.gson.Gson;
import com.yc.damai.dao.AddressDao;
import com.yc.damai.dao.BkOrdersDao;
import com.yc.damai.dao.CartDao;
import com.yc.damai.dao.OrderitemDao;
import com.yc.damai.dao.OrdersDao;
import com.yc.damai.dao.UserDao;
import com.yc.damai.po.Result;
import com.yc.damai.po.TmpOrder;
import com.yc.damai.util.StringUtil;

@WebServlet("/orders.do")

public class OrdersServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private OrdersDao odao = new OrdersDao();

	private OrderitemDao oidao = new OrderitemDao();

	private CartDao cdao = new CartDao();

	private UserDao udao = new UserDao();
	
	private BkOrdersDao bkdao=new BkOrdersDao();

	// 添加订单

	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uid = udao.queryId((Map<String, Object>)request.getSession().getAttribute("loginedUser")) + "";

		System.out.println("uid:"+uid);
		
		boolean defalut=new AddressDao().haveDefault(uid);
		
		if(defalut==false) {
			response.getWriter().append("{\"code\":\"-1\"}");
			return ;
		}
		
		Gson gson=new Gson();
		
		String params=request.getParameter("chosenPro");
		
		ArrayList<Map<String,Object>> list=(ArrayList<Map<String, Object>>) gson.fromJson(params,ArrayList.class);
		
		System.out.println("params:"+params);
		for(Map<String,Object> map:list) {
			System.out.println("map:"+map);
		}

		

		TmpOrder[] tmp=new TmpOrder[list.size()];
		int i=0;
		for(Map<String,Object> map:list) {
			tmp[i]=new TmpOrder();
			tmp[i].setPid(StringUtil.parseInt((double)map.get("pid")));
			tmp[i].setCount(StringUtil.parseInt((double)map.get("count")));
			tmp[i].setUid(Integer.parseInt(uid));
			System.out.println(tmp[i]);
			i++;
		}
		
		
//		odao.insert(uid);
//
//		oidao.insert(uid);
//
//		cdao.deleteByUid(uid);
		
		odao.insert02(tmp);
		
		bkdao.insert(odao.selectLatestId()+"");

		oidao.insert02(tmp);

		cdao.deleteByUid02(tmp);

		response.getWriter().append("{\"code\":\"1\"}");
	}

	// 查询新增的订单

	protected void query(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		String uid = udao.queryId((Map<String, Object>)request.getSession().getAttribute("loginedUser")) + "";
		
		System.out.println("uid:"+uid);
		
		Map<String, Object> ret = new HashMap<>();

		Map<String, Object> orders = odao.queryNewOrders(uid);
		
		System.out.println("orders:"+orders);

		ret.put("orders", orders);

		ret.put("orderitem", oidao.queryByOid("" + orders.get("id")));

		print(response, ret);

	}
	
	/**
	 * 查询用户的所有订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryByUid(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		//System.out.println("loginedUser:"+request.getSession().getAttribute("loginedUser"));
		String uid = udao.queryId((Map<String, Object>)request.getSession().getAttribute("loginedUser")) + "";
		
		Map<String, Object> map;
		List<Map<String, Object>> orderitem;
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> list = odao.queryByUid(uid);  //获取该用户的所有订单
		
		for(Map<String, Object> o:list) {
			String oid=o.get("id")+"";
			orderitem=oidao.queryOiByOid(oid);  //获取每个订单的详细订单id oiid

			map=new HashMap<String, Object>();
			map.put("order",o );
			map.put("orderitem", orderitem);
			
			res.add(map);
		}

		print( response, res);

	}
	
	/**
	 * 根据订单编号查询订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryByOid(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		String oid=request.getParameter("oid");
		
		System.out.println("oid:"+oid);
		
		Map<String, Object> ret = new HashMap<>();

		Map<String, Object> orders = odao.queryOrder(oid);
		
		System.out.println("orders:"+orders);

		ret.put("orders", orders);

		ret.put("orderitem", oidao.queryByOid("" + orders.get("id")));

		print(response, ret);

	}
	
	//确认收货
	protected void mksGetPro(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		String id=request.getParameter("id");
		
		System.out.println("id:"+id);
		
		

		int result = odao.mksGetPro(id);
		if(result==0) {
			print(response, new Result(0,"收货失败"));
		}else {
			print(response, new Result(1,"收货成功"));
		}
		
	}

}