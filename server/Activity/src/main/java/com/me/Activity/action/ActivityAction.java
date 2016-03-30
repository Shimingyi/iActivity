package com.me.Activity.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.me.Activity.dto.Message;
import com.me.Activity.dto.PageDto;
import com.me.Activity.model.Activity;
import com.me.Activity.model.Manager;
import com.me.Activity.service.ActivityService;

@Controller
@RequestMapping("/activity")
public class ActivityAction {

	@Autowired
	private ActivityService activityService;

	private static final Logger log = Logger.getLogger(ActivityAction.class);
	private static final JsonConfig config = new JsonConfig();
	private static final String[] excluded = { "password" };

	@RequestMapping(value = "/listActivity")
	public String ListAcitivity(PageDto dto, HttpServletRequest request) {
		List<Activity> aList;
		aList = activityService.findActivity();
		JSONObject json = new JSONObject();
		config.setExcludes(excluded);
		config.setIgnoreDefaultExcludes(false);
		JSONArray arrays = JSONArray.fromObject(aList, config);
		Message msg = new Message(1, "获取成功");
		json.put("msg", msg);
		json.put("activityList", arrays);
		request.setAttribute("success", json.toString());
		return "success";
	}

	public String ListAcitivityBeforeNow(PageDto dto, HttpServletRequest request) {
		List<Activity> aList;
		if (dto == null)
			aList = activityService.finActivitiyBeforeNow();
		else {
			aList = activityService.finActivitiyBeforeNow(dto.getStartNum(),
					dto.getListCount());
		}
		JSONObject json = new JSONObject();
		config.setExcludes(excluded);
		config.setIgnoreDefaultExcludes(false);
		JSONArray arrays = JSONArray.fromObject(aList, config);
		Message msg = new Message(1, "获取成功");
		json.put("msg", msg);
		json.put("activityList", arrays);
		request.setAttribute("success", json.toString());
		return "success";
	}

	@RequestMapping(value="/find")
	public String Find(Activity activity, HttpServletRequest request,
			HttpSession session){
		Activity _actiActivity =  activityService.findActivity(activity.getId());
		JSONObject json = JSONObject.fromObject(_actiActivity);
		request.setAttribute("success", json.toString());
		return "success";
	}
	
	@RequestMapping(value = "/add")
	public String Add(Activity activity, HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null)
			return error(-11, "管理员未登录", request);
		if (activity == null || activity.getTitle() == null
				|| activity.getContent() == null ) {
			log.info("activity title:" + activity.getTitle());
			log.info("activity content:" + activity.getContent());
			return error(-12, "信息不完整，标题和内容不能为空", request);
		}
		activity.setManager(manager);
		Message msg = activityService.addActivity(activity);
		return error(msg, request);
	}

	@RequestMapping(value = "/update")
	public String Update(Activity activity, HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null)
			return error(-11, "管理员未登录", request);
		if (activity == null || activity.getTitle() == null
				|| activity.getContent() == null || activity.getMax() == null) {
			log.info("activity title:" + activity.getTitle());
			log.info("activity content:" + activity.getContent());
			return error(-12, "信息不完整，标题和内容不能为空", request);
		}
		Activity _activity = activityService.findActivity(activity.getId());
		_activity.setBegintime(activity.getBegintime());
		_activity.setContent(activity.getContent());
		_activity.setEndtime(activity.getEndtime());
		_activity.setImgpath(activity.getImgpath());
		_activity.setTitle(activity.getTitle());
		_activity.setMax(activity.getMax());
		Message msg = activityService.updateActivity(_activity);
		return error(msg, request);
	}

	@RequestMapping(value = "/delete")
	public String Delete(Activity activity, HttpServletRequest request,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null)
			return error(-11, "管理员未登录", request);
		Activity _activity = activityService.findActivity(activity.getId());
		Message msg = activityService.deleteActivity(_activity);
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

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

}
