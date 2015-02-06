package com.spring.redis.vo;

public class TestVO {

	public TestVO(){
		
	}
	
	
	public TestVO(String uid, String name, String sex, int age, String iphone) {
		super();
		this.uid = uid;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.iphone = iphone;
	}

	private String uid;
	
	private String name ;
	
	private String sex;
	
	private int age;
	
	private String iphone;

	public final String getUid() {
		return uid;
	}

	public final void setUid(String uid) {
		this.uid = uid;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getSex() {
		return sex;
	}

	public final void setSex(String sex) {
		this.sex = sex;
	}

	public final int getAge() {
		return age;
	}

	public final void setAge(int age) {
		this.age = age;
	}

	public final String getIphone() {
		return iphone;
	}

	public final void setIphone(String iphone) {
		this.iphone = iphone;
	}
	
	
}
