package com.me.iActivity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.me.iActivity.model.Resume;

@Repository("resumeDao")
public class ResumeDao extends BaseDao<Resume>{

	public List<Resume> listResumes(Integer uid){
		@SuppressWarnings("unchecked")
		List<Resume> rList = this.currentSession()
				.createQuery("select r from ia_resume where r.uid:=uid")
				.setParameter(0, uid)
				.list();
		return rList;
	}
	
}
