package com.me.Activity.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivityServiceTest {

	ApplicationContext ctx = null;
	
	@Before
	public void before()
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	

	@After
	public void after()
	{
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
