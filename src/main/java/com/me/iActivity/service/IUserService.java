package com.me.iActivity.service;

import com.me.iActivity.dto.Message;
import com.me.iActivity.model.User;

public interface IUserService {

	public User findUser(int uid);
	
	public User findUser(String username, String password);
	
	public Message addUser(User user);
	
	public Message updateUser(User user, String oldPassword);
	
	public Message deleteUser(User user);
}
