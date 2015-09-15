package com.me.iActivity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.iActivity.model.Manager;

@Repository("managerDao")
public class ManagerDao extends BaseDao<Manager>{
	
	
	public List<Manager> hasMatchManagers(String username, String password){
		@SuppressWarnings("unchecked")
		List<Manager> mList = this.currentSession()
				.createQuery("select m from ia_manager where m.username=:username and m.password=:password")
				.setParameter(0, username)
				.setParameter(1, password)
				.list();
		return mList;
	}
	
	public List<Manager> hasMatchManagers(String username){
		@SuppressWarnings("unchecked")
		List<Manager> mList = this.currentSession()
				.createQuery("select m from ia_manager where m.username=:username")
				.setParameter(0, username)
				.list();
		return mList;
	}
	
	public List<Manager> hasMatchMail(String mail){
		@SuppressWarnings("unchecked")
		List<Manager> mList = this.currentSession()
				.createQuery("select m from ia_manager where m.mail=:mail")
				.setParameter(0, mail)
				.list();
		return mList;
	}
}
