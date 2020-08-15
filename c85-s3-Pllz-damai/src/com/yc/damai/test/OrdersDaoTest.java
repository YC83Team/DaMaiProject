package com.yc.damai.test;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.OrdersDao;
import com.yc.damai.po.TmpOrder;

class OrdersDaoTest {
	
	OrdersDao dao=new OrdersDao();

	@Test
	void testAlreadyPay() {
		System.out.println(dao.alreadyPay(74+""));
	}

	/**
	 * 测试添加订单主表记录 orders
	 * 自己选择的商品
	 */
	@Test
	void testinsert02() {
		TmpOrder[] tmp=new TmpOrder[2];
		TmpOrder t=new TmpOrder(5,1, 2);
		tmp[0]=t;
		t=new TmpOrder(5,2, 1);
		tmp[1]=t;
		System.out.println(dao.insert02(tmp));
	}
}
