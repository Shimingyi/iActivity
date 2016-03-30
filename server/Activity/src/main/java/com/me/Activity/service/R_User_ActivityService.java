package com.me.Activity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.Activity.dao.ActivityDao;
import com.me.Activity.dao.R_User_ActivityDao;
import com.me.Activity.dao.UserDao;
import com.me.Activity.dto.Message;
import com.me.Activity.model.Activity;
import com.me.Activity.model.R_User_Activity;
import com.me.Activity.model.User;

@Service("r_User_ActivityService")
@Transactional
public class R_User_ActivityService{

	@Autowired
	private R_User_ActivityDao r_User_ActivityDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ActivityDao activityDao;

	
	public List<Activity> findRelationByUser(int uid) {
		// TODO Auto-generated method stub
		User user = userDao.find(User.class, uid);
		if (user == null)
			return null;
		List<R_User_Activity> rList = r_User_ActivityDao
				.listR_User_ActivitiesByUser(uid);
		List<Activity> aList = new ArrayList<Activity>();
		for (int i = 0; i < rList.size(); i++) {
			aList.add(activityDao.find(Activity.class, rList.get(i)
					.getActivity().getId()));
		}
		return aList;
	}

	
	public List<User> findRelationByActivity(int aid) {
		// TODO Auto-generated method stub
		Activity activity = activityDao.find(Activity.class, aid);
		if (activity == null)
			return null;
		List<R_User_Activity> rList = r_User_ActivityDao
				.listR_User_ActivitiesByActivities(aid);
		List<User> uList = new ArrayList<User>();
		for (int i = 0; i < rList.size(); i++) {
			uList.add(userDao.find(User.class, rList.get(i).getUser().getId()));
		}
		return uList;
	}

	
	public R_User_Activity findRelation(int uid, int aid) {
		// TODO Auto-generated method stub
		Activity activity = activityDao.find(Activity.class, aid);
		User user = userDao.find(User.class, uid);
		if (activity == null)
			return null;
		if (user == null)
			return null;
		List<R_User_Activity> rList = r_User_ActivityDao.findOne(uid, aid);
		if(rList.size()>0)
			return rList.get(0);
		return null;
	}

	
	public Message addRelation(R_User_Activity relation) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, relation.getUser().getId());
		Activity activity = activityDao.find(Activity.class, relation
				.getActivity().getId());
		if (user == null) {
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
			return msg;
		}
		if (activity == null) {
			msg.setErrcode(-2);
			msg.setErrmsg("活动不存在");
			return msg;
		}
		activity.setMax(activity.getMax()+1);
		relation.setUser(user);
		relation.setActivity(activity);
		relation.setJointime(new Date());
		activityDao.update(activity);
		r_User_ActivityDao.create(relation);
		msg.setErrcode(1);
		msg.setErrmsg("报名成功");
		return msg;
	}

	
	public Message deleteRelation(R_User_Activity relation) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, relation.getUser().getId());
		Activity activity = activityDao.find(Activity.class, relation
				.getActivity().getId());
		if (user == null) {
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
			return msg;
		}
		if (activity == null) {
			msg.setErrcode(-2);
			msg.setErrmsg("活动不存在");
			return msg;
		}
		R_User_Activity _r_User_Activity = r_User_ActivityDao.find(
				R_User_Activity.class, relation.getId());
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
