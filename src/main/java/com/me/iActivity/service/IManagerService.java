package com.me.iActivity.service;

import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Manager;

public interface IManagerService {

	public Manager findManager(int mid);
	
	public Manager findManager(String username, String password);
	
	public Message addManager(Manager manager);
	
	public Message updateManager(Manager manager, String oldPassword);
	
	public Message deleteManager(Manager manager);
	
}
