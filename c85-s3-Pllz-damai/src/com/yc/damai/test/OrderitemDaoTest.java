package com.yc.damai.test;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.OrderitemDao;
import com.yc.damai.po.TmpOrder;

class OrderitemDaoTest {

	OrderitemDao dao=new OrderitemDao();
	
	@Test
	void testInsert02() {
		TmpOrder[] tmp=new TmpOrder[2];
		TmpOrder t=new TmpOrder(5,1, 2);
		tmp[0]=t;
		t=new TmpOrder(5,2, 1);
		tmp[1]=t;
		System.out.println(dao.insert02(tmp));
	}

}
