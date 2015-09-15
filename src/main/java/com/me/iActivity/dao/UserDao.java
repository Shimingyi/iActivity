package com.me.iActivity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.iActivity.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User>{
	
	public List<User> hasMatchedUsers(String username, String password){
		@SuppressWarnings("unchecked")
		List<User> uList = this.currentSession()
				.createQuery("select u from ia_user where u.username=:name and u.password=:password")
				.setParameter(0, username)
				.setParameter(1, password)
				.list();
		return uList;
	}
	
	public List<User> hasMatchedUsers(String username){
		@SuppressWarnings("unchecked")
		List<User> uList = this.currentSession()
				.createQuery("select u from ia_user where u.username=:name")
				.setParameter(0, username)
				.list();
		return uList;
	}
	
	public List<User> hasMatchNick(String nickname){
		@SuppressWarnings("unchecked")
		List<User> uList = this.currentSession()
				.createQuery("select u from ia_user where u.nickname=:nickname")
				.setParameter(0, nickname)
				.list();
		return uList;
	}
}
