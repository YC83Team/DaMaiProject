package com.yc.damai.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.ProductDao;

class ProductDaoTest {

	ProductDao dao=new ProductDao();
	
	@Test
	void testQueryByList() {
		System.out.println(dao.queryByListByPage(2, 3, 1));
	}

}
