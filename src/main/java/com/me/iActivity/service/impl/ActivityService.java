package com.me.iActivity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.iActivity.dao.ActivityDao;
import com.me.iActivity.dao.ManagerDao;
import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Activity;
import com.me.iActivity.model.Manager;
import com.me.iActivity.service.IActivityService;


@Service("activityService")
@Transactional
public class ActivityService implements IActivityService{

	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ManagerDao managerDao;
	
	@Override
	public Activity findActivity(int aid) {
		// TODO Auto-generated method stub
		return activityDao.find(Activity.class, aid);
	}

	@Override
	public List<Activity> findActivity(int startNum, int listCount) {
		// TODO Auto-generated method stub
		return activityDao.listActivities(startNum, listCount);
	}

	@Override
	public List<Activity> finActivitiyBeforeNow() {
		// TODO Auto-generated method stub
		return activityDao.listActivitiesBeforeNow();
	}

	@Override
	public List<Activity> finActivitiyBeforeNow(int startNum, int listCount) {
		// TODO Auto-generated method stub
		return activityDao.listActivitiesBeforeNow(startNum,listCount);
	}

	@Override
	public List<Activity> findActivityByManager(int mid) {
		// TODO Auto-generated method stub
		Manager manager = managerDao.find(Manager.class, mid);
		if(manager == null)
			return null;
		List<Activity> aList = activityDao.listAcListByMid(mid);
		return aList;
	}

	@Override
	public Message addActivity(Activity activity) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		Manager manager = managerDao.find(Manager.class, activity.getManager().getId());
		if(manager == null){
			msg.setErrcode(-1);
			msg.setErrmsg("添加者不存在");
		}
		activity.setManager(manager);
		activityDao.create(activity);
		return msg;
	}

	
	@Override
	public Message updateActivity(Activity activity) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		Activity _activity = activityDao.find(Activity.class, activity.getId());
		Manager manager = managerDao.find(Manager.class, activity.getManager().getId());
		if(manager == null){
			msg.setErrcode(-1);
			msg.setErrmsg("添加者不存在");
		}
		_activity.setManager(manager);
		_activity.setContent(activity.getContent());
		_activity.setImgpath(activity.getImgpath());
		_activity.setMax(activity.getMax());
		_activity.setTitle(activity.getTitle());
		activityDao.update(_activity);
		msg.setErrcode(1);
		msg.setErrmsg("修改活动成功");
		return msg;
	}

	@Override
	public Message deleteActivity(Activity activity) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		Activity _activity = activityDao.find(Activity.class, activity.getId());
		Manager manager = managerDao.find(Manager.class, activity.getManager().getId());
		if(manager == null){
			msg.setErrcode(-1);
			msg.setErrmsg("添加者不存在");
		}
		if(_activity == null){
			msg.setErrcode(-2);
			msg.setErrmsg("活动不存在");
		}
		activityDao.delete(_activity);
		msg.setErrcode(1);
		msg.setErrmsg("删除成功");
		return msg;
	}

	public ActivityDao getActivityDao() {
		return activityDao;
	}

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	
}
