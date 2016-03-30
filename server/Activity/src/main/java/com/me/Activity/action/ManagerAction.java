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
import com.me.Activity.dto.ManagerDto;
import com.me.Activity.dto.Message;
import com.me.Activity.model.Manager;
import com.me.Activity.service.ManagerService;
import com.me.Activity.util.Base64;

@Controller
@RequestMapping("/manager")
public class ManagerAction {

	@Autowired
	ManagerService managerService;

	private static final Logger log = Logger.getLogger(ManagerAction.class);
	private static final JsonConfig config = new JsonConfig();
	private static final String[] excluded = { "password" };

	@RequestMapping(value = "/login")
	public String Login(LoginDto dto, HttpServletRequest request,
			HttpSession session) {
		log.info("username:" + dto.getUsername());
		Manager manager = managerService.findManager(dto.getUsername(),
				dto.getPassword());
		if (manager != null) {
			config.setExcludes(excluded);
			Message msg = new Message();
			msg.setErrcode(1);
			msg.setErrmsg("登录成功");
			JSONObject json = new JSONObject();
			json.put("msg", msg);
			JSONObject jsons = JSONObject.fromObject(manager, config);
			json.put("info", jsons);
			session.setAttribute("manager", manager);
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
	public String Register(Manager manager, HttpServletRequest request) {
		Message msg = new Message();
		if (manager == null || Base64.isEmpty(manager.getUsername())
				|| Base64.isEmpty(manager.getPassword())) {
			msg.setErrcode(-11);
			msg.setErrmsg("信息不完整，用户名、密码、邮箱为必填项！");
		} else {
			msg = managerService.addManager(manager);
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		request.setAttribute("success", json.toString());
		return "success";
	}

	@RequestMapping(value = "/update")
	public String Update(ManagerDto dto, HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return error(-11, "您还未登录", request);
		}
		if (Base64.isEmpty(dto.getOldPassword())) {
			return error(-12, "原密码不正确", request);
		}
		Manager _manager = new Manager();
		_manager.setId(manager.getId());
		_manager.setUsername(manager.getUsername());
		_manager.setPassword(dto.getPassword());
		_manager.setNickname(dto.getNickname());
		_manager.setMail(dto.getMail());
		_manager.setLevel(dto.getLevel());
		Message msg = managerService.updateManager(_manager,
				dto.getOldPassword());
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

	public ManagerService getManagerService() {
		return managerService;
	}

	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}

}
