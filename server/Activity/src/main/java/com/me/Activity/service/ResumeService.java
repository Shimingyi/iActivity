package com.me.Activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.Activity.dao.ResumeDao;
import com.me.Activity.dao.UserDao;
import com.me.Activity.dto.Message;
import com.me.Activity.model.Resume;
import com.me.Activity.model.User;

@Service("resumeService")
@Transactional
public class ResumeService{

	@Autowired
	private ResumeDao resumeDao;

	@Autowired
	private UserDao userDao;

	
	public Resume findResume(int rid) {
		// TODO Auto-generated method stub
		return resumeDao.find(Resume.class, rid);
	}

	
	public Resume findResumeByUser(int uid) {
		// TODO Auto-generated method stub
		List<Resume> rList = resumeDao.listResumes(uid);
		if (rList.isEmpty())
			return null;
		return rList.get(0);
	}

	
	public Message addResume(Resume resume) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, resume.getOwner().getId());
		if (user == null) {
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
		}
		resume.setOwner(user);
		resumeDao.create(resume);
		msg.setErrcode(1);
		msg.setErrmsg("新建简历成功");
		return msg;
	}

	
	public Message updateResume(Resume resume) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, resume.getOwner().getId());
		if (user == null) {
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
		}
		resume.setOwner(user);
		resumeDao.update(resume);
		msg.setErrcode(1);
		msg.setErrmsg("修改简历成功");
		return msg;
	}

	
	public Message deleteResume(Resume resume) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		Resume _resume = resumeDao.find(Resume.class, resume.getId());
		if (_resume == null) {
			msg.setErrcode(-1);
			msg.setErrmsg("简历不存在");
			return msg;
		}
		resumeDao.delete(_resume);
		msg.setErrcode(1);
		msg.setErrmsg("删除简历成功");
		return msg;
	}

	public ResumeDao getResumeDao() {
		return resumeDao;
	}

	public void setResumeDao(ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
