package com.t226.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.t226.service.building.BuildingService;
import com.t226.service.room.RoomService;
import com.t226.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.t226.pojo.User;
import com.t226.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private BuildingService buildingService;
	@Resource
	private RoomService roomService;


	//进入购房首页
	@RequestMapping("/userMain.html")
	public String userMain(HttpServletRequest request){
		return "/user/main";
	}

	//用户注销
	@RequestMapping("/removeUserSession")
	public String removeUserSession(HttpSession session){
		session.removeAttribute(Constants.USER_SESSION);
		return "redirect:/userLogin.html";
	}

	
}
