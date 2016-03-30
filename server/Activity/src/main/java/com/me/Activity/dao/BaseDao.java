package com.me.Activity.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.me.Activity.util.SessionProcessor;

@Repository("baseDao")
public class BaseDao<T> extends DaoSupport {

	public Serializable create(T obj) {
		Session session = this.currentSession();
		return session.save(obj);
	}

	public void createOrUpdate(T obj) {
		Session session = this.currentSession();
		session.saveOrUpdate(obj);
	}

	public void update(T obj) {
		Session session = this.currentSession();
		session.update(obj);
	}

	public void delete(T obj) {
		this.currentSession().delete(obj);
	}

	@SuppressWarnings("unchecked")
	public T find(Class<? extends T> clazz, Serializable id) {
		return (T) this.currentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> listSql(Class<? extends T> clazz, String sql) {

		return this.currentSession().createSQLQuery(sql).addEntity(clazz)
				.list();

	}

	@SuppressWarnings("unchecked")
	public List<T> listSql(Class<? extends T> clazz, String sql, int start,
			int pagelistnum) {
		Session session = this.currentSession();

		return session.createSQLQuery(sql).addEntity(clazz)
				.setFirstResult(start).setMaxResults(pagelistnum).list();

	}

	@SuppressWarnings("unchecked")
	public List<T> listHql(String hql) {
		Session session = this.currentSession();
		return session.createQuery(hql).list();
		// return session.createSQLQuery(sql).list();

	}

	@SuppressWarnings("unchecked")
	public List<T> listHql(String hql, int start, int pagelistnum) {
		Session session = this.currentSession();

		return session.createQuery(hql).setFirstResult(start)
				.setMaxResults(pagelistnum).list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> list(Class clazz, int start, int pagelistnum) {
		Session session = this.currentSession();

		return session.createQuery("from " + clazz.getName() + " ")
				.setFirstResult(start).setMaxResults(pagelistnum).list();
	}

	public int uniqueResult(String hql) {
		Session session = this.currentSession();

		Number num = (Number) session.createQuery(hql).uniqueResult();
		// return session.createSQLQuery(sql).list();
		return num.intValue();
	}

	public int uniqueResultSql(String sql) {
		Session session = this.currentSession();
		Number num = (Number) session.createSQLQuery(sql).uniqueResult();
		// return session.createSQLQuery(sql).list();
		return num.intValue();

	}

	public void template(SessionProcessor sp) {
		Session session = this.currentSession();
		sp.process(session);

	}

	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}
