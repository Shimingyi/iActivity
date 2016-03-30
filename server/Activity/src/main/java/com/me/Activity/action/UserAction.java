package com.me.Activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.me.Activity.dto.LoginDto;
import com.me.Activity.dto.Message;
import com.me.Activity.dto.UserDto;
import com.me.Activity.model.User;
import com.me.Activity.service.UserService;
import com.me.Activity.util.Base64;

@Controller
@RequestMapping("/user")
public class UserAction {

	@Autowired
	UserService userService;

	private static final Logger log = Logger.getLogger(UserAction.class);
	private static final JsonConfig config = new JsonConfig();
	private static final String[] excluded = { "password" };

	@RequestMapping(value="/login")
	public String Login(LoginDto dto, HttpServletRequest request,
			HttpSession session) {
		log.info("username" + dto.getUsername());
		User user = userService.findUser(dto.getUsername(), dto.getPassword());
		if (user != null) {
			config.setExcludes(excluded);
			Message msg = new Message();
			msg.setErrcode(1);
			msg.setErrmsg("登录成功");
			JSONObject json = new JSONObject();
			json.put("msg", msg);
			JSONObject jsons = JSONObject.fromObject(user, config);
			json.put("info", jsons);
			session.setAttribute("user", user);
			request.setAttribute("success", json.toString());
		} else {
			Message msg = new Message();
			msg.setErrcode(-1);
			msg.setErrmsg("用户名或密码不正确");
			JSONObject json = new JSONObject();
			json.put("msg", msg);
			json.put("info", null);
			request.setAttribute("success", json.toString());
		}
		return "success";
	}

	@RequestMapping(value = "/register")
	public String Register(User user, HttpServletRequest request) {
		Message msg = new Message();
		if (user == null || Base64.isEmpty(user.getUsername())
				|| Base64.isEmpty(user.getPassword())) {
			msg.setErrcode(-11);
			msg.setErrmsg("信息不完整，用户名、密码、邮箱为必填项！");
		} else {
			msg = userService.addUser(user);
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		request.setAttribute("success", json.toString());
		return "success";
	}

	@RequestMapping(value = "/update")
	public String Update(UserDto dto, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return error(-11, "您还未登录", request);
		}
		if (Base64.isEmpty(dto.getOldPassword())) {
			return error(-12, "原密码不正确", request);
		}
		User _user = new User();
		_user.setId(user.getId());
		_user.setUsername(user.getUsername());
		_user.setPassword(dto.getPassword());
		_user.setNickname(dto.getNickname());
		_user.setName(dto.getName());
		Message msg = userService.updateUser(_user, dto.getOldPassword());
		return error(msg, request);
	}

	public String error(int code, String msgs, HttpServletRequest request) {
		Message msg = new Message();
		msg.setErrcode(code);
		msg.setErrmsg(msgs);
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		request.setAttribute("success", json);
		return "success";
	}

	public String error(Message msg, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		request.setAttribute("success", json);
		return "success";
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
