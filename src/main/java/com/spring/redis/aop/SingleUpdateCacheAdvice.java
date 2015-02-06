package com.spring.redis.aop;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.redis.annotation.ReadCacheType;
import com.spring.redis.annotation.UpdateThroughAssignCache;
import com.spring.redis.cache.ICacheService;

/**
 * 添加 或者 更新 都走这个类
 * @author 洋白菜
 *
 */
abstract class SingleUpdateCacheAdvice extends CacheAdvice {

	@Autowired
	private ICacheService cacheService;
	
    protected Object update(final ProceedingJoinPoint pjp) throws Throwable {
    	//验证 缓存 是否开启
        if ( isEnable()) {
            getLogger().info("Cache disabled");
            return pjp.proceed();
        }
        
        Method method = getMethod(pjp);
        UpdateThroughAssignCache cacheable=method.getAnnotation(com.spring.redis.annotation.UpdateThroughAssignCache.class);
        
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
        		
        		Object value = null ;
				value = pjp.proceed();
				if(value != null){
					cacheService.setMap(mapkey,valuekey, value);
				}
				return value ;
        	} else {
        		String key = getCacheKey(namespace, assignedKey, anns,pjp.getArgs());
        		Object value = null ;
        		if(cacheable.cacheType() == ReadCacheType.String ){
    				value = pjp.proceed();
    				if(value != null){
    					cacheService.set(key, value,cacheable.expireTime());
    				}
        			return value ;
        		}
        	}
        }else{  //方法 缓存没开启
        	getLogger().info("Method cache disabled . Name {}", msig.getName());
            return pjp.proceed();
        }
        return null;
    }
    
}
