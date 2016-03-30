package com.me.Activity.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DaoSupport {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

}
