package com.spring.redis.aop;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.redis.annotation.DeleteThroughAssignCache;
import com.spring.redis.annotation.ReadCacheType;
import com.spring.redis.cache.ICacheService;


abstract class SingleDeleteCacheAdvice extends CacheAdvice {

	@Autowired
	private ICacheService cacheService;
	
    protected Object delete(final ProceedingJoinPoint pjp) throws Throwable {
    	//验证 缓存 是否开启
        if ( isEnable()) {
            getLogger().info("Cache disabled");
            return pjp.proceed();
        }
        
        Method method = getMethod(pjp);
        DeleteThroughAssignCache cacheable=method.getAnnotation(com.spring.redis.annotation.DeleteThroughAssignCache.class);
        
        final Signature sig = pjp.getSignature();
        final MethodSignature msig = (MethodSignature) sig;
        
        //验证 方法缓存是否开启
        if(cacheable != null && cacheable.cacheEnable()){
        	// 获取 KEY规则
        	String namespace =  cacheable.namespace();
        	String assignedKey =  cacheable.assignedKey();
        	
        	Annotation [][] anns = method.getParameterAnnotations();
        	if( cacheable.cacheType() == ReadCacheType.Map ){
        		String mapkey = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		String valuekey = getCacheMapValueKey(anns, pjp.getArgs());
        		cacheService.removeMap(mapkey,valuekey);
    		    return pjp.proceed();
        	} else {
        		String key = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		if(cacheable.cacheType() == ReadCacheType.String ){
        			cacheService.remove(key);
        			return pjp.proceed();
        		}
        	}
        }else{  //方法 缓存没开启
        	getLogger().info("Method cache disabled . Name {}", msig.getName());
            return pjp.proceed();
        }
        return null;
    }
    
}
