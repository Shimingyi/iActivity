package com.me.Activity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.Activity.model.Manager;

@Repository("managerDao")
public class ManagerDao extends BaseDao<Manager> {

	public List<Manager> hasMatchManagers(String username, String password) {
		@SuppressWarnings("unchecked")
		List<Manager> mList = this
				.currentSession()
				.createQuery(
						"select m from Manager m where m.username=? and m.password=?")
				.setParameter(0, username).setParameter(1, password).list();
		return mList;
	}

	public List<Manager> hasMatchManagers(String username) {
		@SuppressWarnings("unchecked")
		List<Manager> mList = this
				.currentSession()
				.createQuery(
						"select m from Manager m where m.username=:username")
				.setParameter("username", username).list();
		return mList;
	}

	public List<Manager> hasMatchMail(String mail) {
		@SuppressWarnings("unchecked")
		List<Manager> mList = this.currentSession()
				.createQuery("select m from Manager m where m.mail=:mail")
				.setParameter("mail", mail).list();
		return mList;
	}
}
