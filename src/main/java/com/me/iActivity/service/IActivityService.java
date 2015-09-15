package com.me.iActivity.service;

import java.util.List;

import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Activity;

public interface IActivityService {

	//一个测试
	public Activity findActivity(int aid);
	
	public List<Activity> findActivity(int startNum, int listCount);
	
	public List<Activity> finActivitiyBeforeNow();
	
	public List<Activity> finActivitiyBeforeNow(int startNum, int listCount);

	public List<Activity> findActivityByManager(int mid);
	
	public Message addActivity(Activity activity);
	
	public Message updateActivity(Activity activity);
	
	public Message deleteActivity(Activity activity);
}
