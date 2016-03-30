package com.me.Activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.me.Activity.dto.Message;
import com.me.Activity.model.Resume;
import com.me.Activity.model.User;
import com.me.Activity.service.ResumeService;
import com.me.Activity.util.Base64;

@Controller
@RequestMapping("/resume")
public class ResumeAction {

	@Autowired
	ResumeService resumeService;

	private static final Logger log = Logger.getLogger(ResumeAction.class);
	private static final JsonConfig config = new JsonConfig();
	private static final String[] excluded = { "password" };

	@RequestMapping(value = "/add")
	public String Add(Resume resume, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null)
			return error(-11, "您还未登录", request);
		if (resume == null || Base64.isEmpty(resume.getStunum())
				|| Base64.isEmpty(resume.getPhone())) {
			log.info("The stuId of the resume:" + resume.getStunum());
			return error(-12, "学号、电话不能为空", request);
		}
		resume.setOwner(user);
		Message msg = resumeService.addResume(resume);
		return error(msg, request);
	}

	@RequestMapping(value = "/update")
	public String Update(Resume resume, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null)
			return error(-11, "用户未登录", request);
		if (resume == null || Base64.isEmpty(resume.getStunum())
				|| Base64.isEmpty(resume.getPhone())) {
			log.info("The stuId of the resume:" + resume.getStunum());
			return error(-12, "学号、电话不能为空", request);
		}
		Resume _resume = resumeService.findResume(resume.getId());
		if (_resume.getOwner().getId() != user.getId())
			return error(-13, "不是你的简历不能修改", request);
		//_resume.setBirth(resume.getBirth());
		_resume.setCollege(resume.getCollege());
		_resume.setIdcard(resume.getIdcard());
		_resume.setMail(resume.getMail());
		_resume.setOwner(user);
		_resume.setPhone(resume.getPhone());
		_resume.setSex(resume.getSex());
		_resume.setStunum(resume.getStunum());
		_resume.setYear(resume.getYear());
		Message msg = resumeService.updateResume(_resume);
		return error(msg, request);
	}

	@RequestMapping(value = "/find")
	public String Find(User user, HttpServletRequest request,
			HttpSession session) {
		User _user = (User) session.getAttribute("user");
		if (_user == null)
			return error(-11, "用户未登录", request);
		if (user == null)
			user = _user;
		Resume resume = resumeService.findResumeByUser(_user.getId());
		if (resume == null) {
			resume = new Resume();
			resume.setOwner(_user);
		}
		JSONObject json = new JSONObject();
		config.setExcludes(excluded);
		config.setIgnoreDefaultExcludes(false);
		Message msg = new Message(1, "获取成功");
		json.put("msg", msg);
		json.put("resume", resume);
		request.setAttribute("success", json.toString());
		return "success";
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

	public ResumeService getResumeService() {
		return resumeService;
	}

	public void setResumeService(ResumeService resumeService) {
		this.resumeService = resumeService;
	}

}
