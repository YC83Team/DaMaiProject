package com.yc.damai.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.AddressDao;

class AddressDaoTest {

	AddressDao dao=new AddressDao();
	
	@Test
	void testHaveDefault() {
		System.out.println(dao.haveDefault("7"));
	}

}
