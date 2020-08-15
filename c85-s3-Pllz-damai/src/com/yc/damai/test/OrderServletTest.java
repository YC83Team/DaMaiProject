package com.yc.damai.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.CartDao;
import com.yc.damai.dao.OrderitemDao;
import com.yc.damai.dao.OrdersDao;

class OrderServletTest {

	private OrdersDao odao = new OrdersDao();

	private OrderitemDao oidao = new OrderitemDao();

	private CartDao cdao = new CartDao();
	
	@Test
	void testQuery() {
		String uid = "2"; // 用户id 从会话中获取 loginedUser.getId(),

		Map<String, Object> ret = new HashMap<>();

		Map<String, Object> orders = odao.queryNewOrders(uid);

		ret.put("orders", orders);

		ret.put("orderitem", oidao.queryByOid("" + orders.get("id")));
		
		System.out.println(ret);
	}

}
