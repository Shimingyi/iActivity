package com.me.Activity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.Activity.model.Activity;

@Repository("activityDao")
public class ActivityDao extends BaseDao<Activity> {

	public List<Activity> listActivities() {
		@SuppressWarnings("unchecked")
		List<Activity> aList = this.currentSession()
				.createQuery("select a from Activity a").list();
		return aList;
	}

	public List<Activity> listActivities(int startNum, int listCount) {
		@SuppressWarnings("unchecked")
		List<Activity> aList = this.currentSession()
				.createQuery("select a from Activity a")
				.setFirstResult(startNum).setMaxResults(listCount).list();
		return aList;
	}

	public List<Activity> listActivitiesBeforeNow() {
		@SuppressWarnings("unchecked")
		List<Activity> aList = this
				.currentSession()
				.createQuery(
						"select a from Activity a where TO_DAYS(NOW()) - TO_DAYS(a.endtime) < 0 ")
				.list();
		return aList;
	}

	public List<Activity> listActivitiesBeforeNow(int startNum, int listCount) {
		@SuppressWarnings("unchecked")
		List<Activity> aList = this
				.currentSession()
				.createQuery(
						"select a from Activity a where TO_DAYS(NOW()) - TO_DAYS(a.endtime) < 0 ")
				.setFirstResult(startNum).setMaxResults(listCount).list();
		return aList;
	}

	public List<Activity> listAcListByMid(Integer mid) {
		@SuppressWarnings("unchecked")
		List<Activity> aList = this.currentSession()
				.createQuery("select a from Activity a where a.mid=?")
				.setParameter(0, mid).list();
		return aList;
	}

	public List<Activity> listAcListByMid(Integer mid, int startNum,
			int listCount) {
		@SuppressWarnings("unchecked")
		List<Activity> aList = this.currentSession()
				.createQuery("select a from Activity a where a.mid=?")
				.setParameter(0, mid).setFirstResult(startNum)
				.setMaxResults(listCount).list();
		return aList;
	}

}
