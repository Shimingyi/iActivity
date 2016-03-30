package com.me.Activity.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.me.Activity.dto.Message;
import com.me.Activity.model.Activity;
import com.me.Activity.model.R_User_Activity;
import com.me.Activity.model.User;
import com.me.Activity.service.ActivityService;
import com.me.Activity.service.R_User_ActivityService;

@Controller
@RequestMapping("/r_user_activity")
public class R_User_ActicityAction {

	@Autowired
	private R_User_ActivityService r_User_ActivityService;

	@Autowired
	private ActivityService activityService;

	private static final JsonConfig config = new JsonConfig();
	private static final String[] excluded = { "password" };

	@RequestMapping("/search")
	public String Join(HttpServletRequest request,
			HttpSession session){
		User user = (User) session.getAttribute("user");
		if (user == null)
			return error(-11, "用户未登录", request);
		List<Activity> aList = r_User_ActivityService.findRelationByUser(user.getId());
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
	
	@RequestMapping("/join")
	public String Join(Activity activity, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		Activity _activity = activityService.findActivity(activity.getId());
		if (user == null)
			return error(-11, "用户未登录", request);
		R_User_Activity r_User_Acticity = new R_User_Activity();
		r_User_Acticity.setActivity(_activity);
		r_User_Acticity.setUser(user);
		r_User_Acticity.setJointime(new Date());
		Message msg = r_User_ActivityService.addRelation(r_User_Acticity);
		return error(msg, request);
	}
	
	@RequestMapping("/check")
	public String Check(Activity activity,HttpServletRequest request,
			HttpSession session){
		User user = (User)session.getAttribute("user");
		Activity _activity = activityService.findActivity(activity.getId());
		if (user == null)
			return error(-11, "用户未登录", request);
		R_User_Activity r_User_Activity = r_User_ActivityService.findRelation(user.getId(), _activity.getId());
		if(r_User_Activity == null)
			return error(-12,"未参加", request);
		return error(1,"已经参加", request);
	}
	
	@RequestMapping("/quit")
	public String Quit(Activity activity, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null)
			return error(-11, "用户未登录", request);
		R_User_Activity r_User_Activity = r_User_ActivityService.findRelation(
				user.getId(), activity.getId());
		if (r_User_Activity == null)
			return error(-11, "你没有参加此活动", request);
		r_User_ActivityService.deleteRelation(r_User_Activity);
		return error(1, "取消活动成功", request);
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

	public R_User_ActivityService getR_User_ActivityService() {
		return r_User_ActivityService;
	}

	public void setR_User_ActivityService(
			R_User_ActivityService r_User_ActivityService) {
		this.r_User_ActivityService = r_User_ActivityService;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

}
