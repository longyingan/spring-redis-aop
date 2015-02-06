package com.spring.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存 方法注解
 * @author 洋白菜
 *
 */
@Target({ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface ReadThroughAssignCache {  
	
	/**
	 * 命名 空间  也是 KEY的前缀
	 * @return
	 */
	String namespace() default AnnotationConstants.DEFAULT_STRING;
	/**
	 * 存储的 KEY
	 * @return
	 */
	String assignedKey() default AnnotationConstants.DEFAULT_STRING;
	/**
	 * 缓存 类型
	 * @return
	 */
	ReadCacheType cacheType() default ReadCacheType.String ;
	/**
	 * 有效时间 默认 永久有效
	 * @return
	 */
	int expireTime() default 0;  
	
	/**
	 * 是否启用  默认 开启
	 * @return
	 */
	boolean cacheEnable() default true;
	
	/**
	 * 默认 value 存储类型
	 * @return
	 */
	Class<?> valueclass() default String.class;
}
