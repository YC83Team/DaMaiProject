package com.yc.damai.test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.CartDao;
import com.yc.damai.po.TmpOrder;

class CartDaoTest {
	
	CartDao dao=new CartDao();

	@Test
	void testDeleteByUid02() {
		TmpOrder[] tmp=new TmpOrder[2];
		TmpOrder t=new TmpOrder(6,12, 1);
		tmp[0]=t;
		t=new TmpOrder(6,55, 2);
		tmp[1]=t;
		System.out.println(dao.deleteByUid02(tmp));
	}

}
