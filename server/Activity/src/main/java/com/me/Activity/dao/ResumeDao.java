package com.me.Activity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.Activity.model.Resume;

@Repository("resumeDao")
public class ResumeDao extends BaseDao<Resume> {

	public List<Resume> listResumes(Integer uid) {
		@SuppressWarnings("unchecked")
		List<Resume> rList = this.currentSession()
				.createQuery("select r from Resume r where r.owner.id=?")
				.setParameter(0, uid).list();
		return rList;
	}

}
