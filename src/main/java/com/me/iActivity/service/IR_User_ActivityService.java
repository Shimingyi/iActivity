package com.me.iActivity.service;

import java.util.List;

import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Activity;
import com.me.iActivity.model.R_User_Activity;
import com.me.iActivity.model.User;

public interface IR_User_ActivityService {

	public List<Activity> findRelationByUser(int uid);
	
	public List<User> findRelationByActivity(int aid);
	
	public Message addRelation(R_User_Activity relation);
	
	public Message deleteRelation(R_User_Activity relation);
}
