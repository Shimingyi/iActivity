package com.me.Activity.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.me.Activity.dto.Message;
import com.me.Activity.service.UserService;
import com.me.Activity.model.User;

public class UserServiceTest {

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
	public void Addtest() {
		UserService us = ctx.getBean(UserService.class);
		/*
		User user =  new User();
		user.setName("小明");
		user.setNickname("rubbly");
		user.setPassword("3231459");
		user.setUsername("rubbly");
		Message msg = us.addUser(user);
		System.out.println(msg.toString());
		*/
		User user =  us.findUser("rubbly", "3231459");
		if(user != null){
			System.out.println(user.getName());
		}
	}

}
