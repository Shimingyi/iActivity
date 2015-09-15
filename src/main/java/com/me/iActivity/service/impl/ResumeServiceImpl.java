package com.me.iActivity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.iActivity.dao.ResumeDao;
import com.me.iActivity.dao.UserDao;
import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Resume;
import com.me.iActivity.model.User;
import com.me.iActivity.service.IResumeService;

@Service("resumeService")
@Transactional
public class ResumeServiceImpl implements IResumeService{

	@Autowired
	private ResumeDao resumeDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public Resume findResume(int rid) {
		// TODO Auto-generated method stub
		return resumeDao.find(Resume.class, rid);
	}

	@Override
	public Resume findResumeByUser(int uid) {
		// TODO Auto-generated method stub
		List<Resume> rList = resumeDao.listResumes(uid);
		if(rList.isEmpty())
			return null;
		return rList.get(0);
	}
	
	@Override
	public Message addResume(Resume resume) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, resume.getOwner().getId());
		if(user == null){
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
		}
		resume.setOwner(user);
		resumeDao.create(resume);
		msg.setErrcode(1);
		msg.setErrmsg("新建简历成功");
		return msg;
	}

	@Override
	public Message updateResume(Resume resume) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User user = userDao.find(User.class, resume.getOwner().getId());
		if(user == null){
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在");
		}
		resume.setOwner(user);
		resumeDao.update(resume);
		msg.setErrcode(1);
		msg.setErrmsg("修改简历成功");
		return msg;
	}

	@Override
	public Message deleteResume(Resume resume) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		Resume _resume = resumeDao.find(Resume.class, resume.getId());
		if(_resume == null){
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
