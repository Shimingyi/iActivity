package com.me.iActivity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.iActivity.dao.ActivityDao;
import com.me.iActivity.dao.R_User_ActivityDao;
import com.me.iActivity.dao.UserDao;
import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Activity;
import com.me.iActivity.model.R_User_Activity;
import com.me.iActivity.model.User;
import com.me.iActivity.service.IR_User_ActivityService;


@Service("r_User_ActivityService")
@Transactional
public class R_User_ActivityServiceImpl implements IR_User_ActivityService{

	@Autowired
	private R_User_ActivityDao r_User_ActivityDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public List<Activity> findRelationByUser(int uid) {
		// TODO Auto-generated method stub
		User user = userDao.find(User.class, uid);
		if(user == null)
			return null;
		List<R_User_Activity> rList = r_User_ActivityDao.listR_User_ActivitiesByUser(uid);
		List<Activity> aList = new ArrayList<Activity>();
		for(int i = 0;i<rList.size();i++){
			aList.add(activityDao.find(Activity.class, rList.get(i).getActivity().getId()));
		}
		return aList;
	}

	@Override
	public List<User> findRelationByActivity(int aid) {
		// TODO Auto-generated method stub
		Activity activity = activityDao.find(Activity.class, aid);
		if(activity == null)
			return null;
		List<R_User_Activity> rList = r_User_ActivityDao.listR_User_ActivitiesByActivities(aid);
		List<User> uList = new ArrayList<User>();
		for (int i = 0; i < rList.size(); i++) {
			uList.add(userDao.find(User.class, rList.get(i).getUser().getId()));
		}
		return uList;
	}

	@Override
	public Message addRelation(R_User_Activity relation) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, relation.getUser().getId());
		Activity activity = activityDao.find(Activity.class, relation.getActivity().getId());
		if(user == null){
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
			return msg;
		}
		if(activity == null){
			msg.setErrcode(-2);
			msg.setErrmsg("活动不存在");
			return msg;
		}
		relation.setUser(user);
		relation.setActivity(activity);
		relation.setJointime(new Date());
		r_User_ActivityDao.create(relation);
		msg.setErrcode(1);
		msg.setErrmsg("报名成功");
		return msg;
	}

	@Override
	public Message deleteRelation(R_User_Activity relation) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, relation.getUser().getId());
		Activity activity = activityDao.find(Activity.class, relation.getActivity().getId());
		if(user == null){
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
			return msg;
		}
		if(activity == null){
			msg.setErrcode(-2);
			msg.setErrmsg("活动不存在");
			return msg;
		}
		R_User_Activity _r_User_Activity = r_User_ActivityDao.find(R_User_Activity.class, relation.getId());
		r_User_ActivityDao.delete(_r_User_Activity);
		msg.setErrcode(1);
		msg.setErrmsg("删除成功");
		return msg;
	}

	
	public R_User_ActivityDao getR_User_ActivityDao() {
		return r_User_ActivityDao;
	}

	public void setR_User_ActivityDao(R_User_ActivityDao r_User_ActivityDao) {
		this.r_User_ActivityDao = r_User_ActivityDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ActivityDao getActivityDao() {
		return activityDao;
	}

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

}
