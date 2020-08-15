package com.yc.damai.test;

import org.junit.jupiter.api.Test;

import com.yc.damai.dao.CommentDao;
import com.yc.damai.po.DmComment;

class CommentDaoTest {

	CommentDao dao=new CommentDao();
	
	@Test
	void testInsert() {
		DmComment dc=new DmComment();
		
		dc.setUid(5);
		dc.setOiid(89);
		dc.setComment("真好看");
		dc.setLevel(5.0);
		System.out.println(dc);
		System.out.println(dao.insert(dc));
	}
	
	
	@Test
	void testQueryByPid() {
		
		System.out.println(dao.queryByPid("12"));
	}
}
