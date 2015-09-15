package com.me.iActivity.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.iActivity.dao.UserDao;
import com.me.iActivity.dto.Message;
import com.me.iActivity.model.User;
import com.me.iActivity.service.IUserService;
import com.me.iActivity.units.Base64;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public User findUser(int uid) {
		// TODO Auto-generated method stub
		return userDao.find(User.class, uid);
	}

	@Override
	public User findUser(String username, String password) {
		// TODO Auto-generated method stub
		List<User> ulist = userDao.hasMatchedUsers(username, Base64.EncryptMd5(password));
		if(ulist.isEmpty()) 
			return null;
		else 
			return ulist.get(0);
	}

	@Override
	public Message addUser(User user) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		String username = user.getUsername();
		String password = user.getPassword();
		List<User> uList = userDao.hasMatchedUsers(username);
		if(uList.isEmpty()){
			msg.setErrcode(-1);
			msg.setErrmsg("用户名已存在");
		}
		user.setPassword(Base64.EncryptMd5(password));
		userDao.create(user);
		msg.setErrcode(1);
		msg.setErrmsg("注册成功！");
		return msg;
	}

	@Override
	public Message updateUser(User user, String oldPassword) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		User _user = userDao.find(User.class, user.getId());
		if(!_user.getPassword().equals(Base64.EncryptMd5(oldPassword))){
			msg.setErrcode(-1);
			msg.setErrmsg("和原密码不匹配");
			return msg;
		}
		_user.setName(user.getName());
		_user.setNickname(user.getNickname());
		_user.setPassword(Base64.EncryptMd5(user.getPassword()));
		userDao.update(_user);
		msg.setErrcode(1);
		msg.setErrmsg("更改账户信息成功");
		return msg;
	}

	@Override
	public Message deleteUser(User user) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		List<User> uList = userDao.hasMatchedUsers(user.getUsername());
		if(uList.isEmpty()){
			msg.setErrcode(-1);
			msg.setErrmsg("用户不存在，删除失败");
			return msg;
		}
		userDao.delete(uList.get(0));
		msg.setErrcode(1);
		msg.setErrmsg("删除成功");
		return msg;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
