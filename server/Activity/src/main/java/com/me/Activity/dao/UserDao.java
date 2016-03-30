package com.me.Activity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.Activity.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User> {

	public List<User> hasMatchedUsers(String username, String password) {
		@SuppressWarnings("unchecked")
		List<User> uList = this
				.currentSession()
				.createQuery(
						"select u from User u where u.username=:name and u.password=:password")
				.setParameter("name", username)
				.setParameter("password", password)
				.list();
		return uList;
	}

	public List<User> hasMatchedUsers(String username) {
		@SuppressWarnings("unchecked")
		List<User> uList = this.currentSession()
				.createQuery("select u from User u where u.username=:name")
				.setParameter("name", username).list();
		return uList;
	}

	public List<User> hasMatchNick(String nickname) {
		@SuppressWarnings("unchecked")
		List<User> uList = this
				.currentSession()
				.createQuery("select u from User u where u.nickname=:nickname")
				.setParameter("nickname", nickname).list();
		return uList;
	}
}
