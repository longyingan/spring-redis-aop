package com.spring.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.spring.redis.impl.GetUserImpl;
import com.spring.redis.vo.TestVO;

public class TestMain {
   
	public static void main(String[] args) throws InterruptedException {
		String [] iem = {"classpath:application-aop.xml","classpath:application-propertyFile.xml","classpath:application-redis.xml"};
		ApplicationContext act=new FileSystemXmlApplicationContext(iem); 
		
		Map<String, TestVO> maps = new HashMap<String, TestVO>();
		maps.put("222", new TestVO("222", "lisi", "男", 21, "15648921562"));
		maps.put("333", new TestVO("333", "zhangsan", "女", 24, "154892614789"));
		maps.put("444", new TestVO("444", "mito", "男", 22, "12364596122"));
		maps.put("555", new TestVO("555", "yes", "女", 40, "15962351489"));
		
		GetUserImpl.init = maps ;
		
		//测试 查询
		IGetUser g=(IGetUser) act.getBean("getUserServer"); 
		TestVO vo = g.get("333");
		System.out.println(vo.getSex());
		
		//测试删除
		System.out.println(g.delete("333"));
		//验证删除成功否
		System.out.println(g.get("333"));
		System.out.println(GetUserImpl.init.size());
		
		//测试更新
		TestVO uvo = g.update("222", new TestVO("222", "这事更新", "男", 32, "529579572"));
		System.out.println(uvo.getName());
		//验证更新是否成功
		System.out.println(g.get("222").getIphone());
		
		//测试添加
		TestVO avo = g.add("777", new TestVO("777","hi hello","女",18,"456746554"));
		System.out.println(avo.getName());
		//验证是否添加成功
		System.out.println(g.get("777").getIphone());
		System.out.println(GetUserImpl.init.size());
	}
}
