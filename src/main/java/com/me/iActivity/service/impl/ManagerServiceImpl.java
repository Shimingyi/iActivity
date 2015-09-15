package com.me.iActivity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.iActivity.dao.ManagerDao;
import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Manager;
import com.me.iActivity.service.IManagerService;
import com.me.iActivity.units.Base64;


@Service("managerService")
@Transactional
public class ManagerServiceImpl implements IManagerService{


	@Autowired
	private ManagerDao managerDao;
	
	@Override
	public Manager findManager(int mid) {
		// TODO Auto-generated method stub
		return managerDao.find(Manager.class, mid);
	}

	@Override
	public Manager findManager(String username, String password) {
		// TODO Auto-generated method stub
		List<Manager> mList = managerDao.hasMatchManagers(username, Base64.EncryptMd5(password));
		if(mList.isEmpty()) 
			return null;
		else 
			return mList.get(0);
	}

	@Override
	public Message addManager(Manager manager) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		List<Manager> mList = managerDao.hasMatchManagers(manager.getUsername());
		if(mList.isEmpty()){
			msg.setErrcode(-1);
			msg.setErrmsg("用户名已经存在");
			return msg;
		}
		mList = managerDao.hasMatchMail(manager.getMail());
		if(mList.isEmpty()){
			msg.setErrcode(-1);
			msg.setErrmsg("邮箱已被注册");
			return msg;
		}
		manager.setPassword(Base64.EncryptMd5(manager.getPassword()));
		managerDao.create(manager);
		msg.setErrcode(1);
		msg.setErrmsg("注册管理员用户成功");
		return msg;
	}

	@Override
	public Message updateManager(Manager manager, String oldPassword) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		Manager _manager = managerDao.find(Manager.class, manager.getId());
		if(!_manager.getPassword().equals(Base64.EncryptMd5(oldPassword))){
			msg.setErrcode(-1);
			msg.setErrmsg("原密码输入错误");
			return msg;
		}
		if(manager.getMail() != null){
			List<Manager> mList = managerDao.hasMatchMail(manager.getMail());
			if(!mList.isEmpty()){
				msg.setErrcode(-2);
				msg.setErrmsg("邮箱已被注册");
				return msg;
			}
		}
		_manager.setLevel(manager.getLevel());
		_manager.setMail(manager.getMail());
		_manager.setNickname(manager.getNickname());
		_manager.setPassword(Base64.EncryptMd5(manager.getPassword()));
		managerDao.update(_manager);
		msg.setErrcode(1);
		msg.setErrmsg("更新管理员信息成功");
		return msg;
	}


	@Override
	public Message deleteManager(Manager manager) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		List<Manager> mList = managerDao.hasMatchManagers(manager.getUsername());
		if(mList.isEmpty()){
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在，删除失败");
			return msg;
		}
		managerDao.delete(mList.get(0));
		msg.setErrcode(1);
		msg.setErrmsg("删除成功");
		return msg;
	}

	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

}
