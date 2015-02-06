package com.spring.redis;

import com.spring.redis.annotation.ReadCacheType;
import com.spring.redis.annotation.ReadThroughAssignCache;

/**
 * Hello world!
 *
 */
public class App 
{
	
	@ReadThroughAssignCache(cacheType=ReadCacheType.Map)
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
