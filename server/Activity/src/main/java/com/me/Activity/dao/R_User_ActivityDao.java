package com.me.Activity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.Activity.model.R_User_Activity;

@Repository("r_User_ActivityDao")
public class R_User_ActivityDao extends BaseDao<R_User_Activity> {

	public List<R_User_Activity> findOne(Integer uid, Integer aid) {
		@SuppressWarnings("unchecked")
		List<R_User_Activity> rList = this
				.currentSession()
				.createQuery(
						"select r from R_User_Activity r where r.user.id=? and r.activity.id=?")
				.setParameter(0, uid).setParameter(1, aid).list();
		return rList;
	}

	public List<R_User_Activity> listR_User_ActivitiesByUser(Integer uid) {
		@SuppressWarnings("unchecked")
		List<R_User_Activity> rList = this
				.currentSession()
				.createQuery(
						"select r from R_User_Activity r where r.user.id=?")
				.setParameter(0, uid).list();
		return rList;
	}

	public List<R_User_Activity> listR_User_ActivitiesByActivities(Integer aid) {
		@SuppressWarnings("unchecked")
		List<R_User_Activity> rList = this
				.currentSession()
				.createQuery(
						"select r from R_User_Activity r where r.activity.id=?")
				.setParameter(0, aid).list();
		return rList;
	}
}
