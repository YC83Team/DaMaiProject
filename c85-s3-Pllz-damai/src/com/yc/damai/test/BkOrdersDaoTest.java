package com.yc.damai.test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.BkOrdersDao;

class BkOrdersDaoTest {
	
	BkOrdersDao dao=new BkOrdersDao();

	@Test
	void testInsert() {
		dao.insert("85");
	}
	
	@Test
	void testQuery() {
		System.out.println(dao.query("1","2","",""));
	}

	@Test
	void testcountPage() {
		System.out.println(dao.countPage("84", ""));
	}
	
	@Test
	void testsave() {
		System.out.println(dao.save("74", "张三", "宁愿", "1234"));
	}
}
