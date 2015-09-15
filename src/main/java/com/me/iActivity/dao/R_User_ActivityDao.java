package com.me.iActivity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.iActivity.model.R_User_Activity;

@Repository("r_User_ActivityDao")
public class R_User_ActivityDao extends BaseDao<R_User_Activity>{

	public List<R_User_Activity> listR_User_ActivitiesByUser(Integer uid){
		@SuppressWarnings("unchecked")
		List<R_User_Activity> rList = this.currentSession()
				.createQuery("select r from ia_r_user_activity where r.uid:=uid")
				.setParameter(0, uid)
				.list();
		return rList;
	}
	
	public List<R_User_Activity> listR_User_ActivitiesByActivities(Integer aid){
		@SuppressWarnings("unchecked")
		List<R_User_Activity> rList = this.currentSession()
				.createQuery("select r from ia_r_user_activity where r.aid:=aid")
				.setParameter(0, aid)
				.list();
		return rList;
	}
}
