package com.spring.redis.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

import com.spring.redis.annotation.AnnotationConstants;
import com.spring.redis.annotation.ParameterMapKeyProvider;
import com.spring.redis.annotation.ParameterValueKeyProvider;


/**
 * 切面 基类
 * @author Administrator
 *
 */
public abstract class CacheAdvice  {

    public static final String ENABLE_CACHE_PROPERTY = "ssm.cache.enable";

    public static final String ENABLE_CACHE_PROPERTY_VALUE = "true";
    
    /**
     * 缓存 是否开启
     * @return
     */
    protected boolean isEnable() {
        return ENABLE_CACHE_PROPERTY_VALUE.equals(System.getProperty(ENABLE_CACHE_PROPERTY));
    }
    
    /**
     * 获取 方法 注解参数 和参数的注解
     * @param pjp
     * @return
     */
    protected Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		@SuppressWarnings("rawtypes")
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}
    
    protected String getCacheKey(String namespace,String assignedKey,Annotation[][] ans,Object [] args){
    	StringBuilder sb = new StringBuilder();
    	if( namespace != null && !namespace.equals("") && !namespace.equals(AnnotationConstants.DEFAULT_STRING)) {
    		sb.append(namespace);
    		sb.append("_");
    	}
    	if( assignedKey != null && !assignedKey.equals("") && !assignedKey.equals(AnnotationConstants.DEFAULT_STRING) ) {
    		sb.append(assignedKey);
    		sb.append("_");
    	}
    	
    	if( ans != null && ans.length >0){
    		for(Annotation[] anitem : ans){
    			//方知 注解过多 以此提前出循环
    			if(anitem.length > 0 && anitem[0] instanceof ParameterValueKeyProvider){
	            	for(Annotation an : anitem){
            			ParameterValueKeyProvider vk = (ParameterValueKeyProvider)an;
            			sb.append(args[vk.order()]);
            			sb.append("_");
	            	}
	            	break;
    			}
            }
    	}
    	
    	return sb.toString();
    }
    
    protected String getCacheMapValueKey(Annotation[][] ans,Object [] args){
    	StringBuilder sb = new StringBuilder();
    	if( ans != null && ans.length >0){
    		for(Annotation[] anitem : ans){
    			//方知 注解过多 以此提前出循环
    			if(anitem.length > 0 && anitem[0] instanceof ParameterMapKeyProvider){
	            	for(Annotation an : anitem){
	            		ParameterMapKeyProvider vk = (ParameterMapKeyProvider)an;
            			sb.append(args[vk.order()]);
            			sb.append("_");
	            	}
	            	break;
    			}
            }
    	}
    	
    	return sb.toString();
    }
    
    protected void warn(final Exception e, final String format, final Object... args) {
        if (getLogger().isWarnEnabled()) {
            getLogger().warn(String.format(format, args), e);
        }
    }
    
    protected abstract Logger getLogger();

}
