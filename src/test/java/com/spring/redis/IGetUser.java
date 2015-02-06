package com.spring.redis;

import com.spring.redis.vo.TestVO;

public interface IGetUser {

	public TestVO get(String uid);
	
	public TestVO add(String uid,TestVO vo);
	
	public boolean delete(String uid);
	
	public TestVO update(String uid,TestVO vo);
}
