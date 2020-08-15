package com.yc.damai.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.CategoryDao;

class CategoryDaoTest {

	@Test
	void testQueryByFirstCat() {
		System.out.println(new CategoryDao().queryByFirstCat());
	}

	@Test
	void testQueryBySecondCat() {
		System.out.println(new CategoryDao().queryBySecondCat(1));
	}
	
	
}
