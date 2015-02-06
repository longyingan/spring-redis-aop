package com.spring.redis.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.redis.IGetUser;
import com.spring.redis.annotation.DeleteThroughAssignCache;
import com.spring.redis.annotation.ParameterValueKeyProvider;
import com.spring.redis.annotation.ReadCacheType;
import com.spring.redis.annotation.ReadThroughAssignCache;
import com.spring.redis.annotation.UpdateThroughAssignCache;
import com.spring.redis.vo.TestVO;

@Service("getUserServer")
public class GetUserImpl  implements IGetUser{

	public static Map<String, TestVO> init = null ;
	
	@ReadThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String,valueclass =TestVO.class,expireTime=10)
	@Override
	public TestVO get(@ParameterValueKeyProvider String uid) {
		System.out.println("数据库查询被执行.....");
		return init.get(uid);
	}

	@UpdateThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String,valueclass =TestVO.class)
	@Override
	public TestVO add(@ParameterValueKeyProvider String uid, TestVO value) {
		System.out.println("数据库添加被执行.....");
		value.setUid(uid);
		init.put(uid, value);
		return value;
	}

	@DeleteThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String)
	@Override
	public boolean delete(@ParameterValueKeyProvider String uid) {
		System.out.println("数据库删除被执行.....");
		init.remove(uid);
		return true;
	}

	@UpdateThroughAssignCache(namespace="GETUSER",cacheType = ReadCacheType.String,valueclass =TestVO.class)
	@Override
	public TestVO update(@ParameterValueKeyProvider String uid, TestVO vo) {
		System.out.println("数据库更新被执行.....");
		init.put(uid, vo);
		return vo;
	}

}
