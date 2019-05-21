package com.t226.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.t226.service.building.BuildingService;
import com.t226.service.room.RoomService;
import com.t226.tools.Constants;
import com.t226.tools.Idcard;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.t226.pojo.User;
import com.t226.service.user.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

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


	//查询用户个人信息
	@RequestMapping("/showUserInfo.html")
	public String showUserInfo(){
		return "/user/showUserInfo";
	}


	//完善用户信息
	@RequestMapping("/updateUserInfo.html")
	public String updateUserInfo(){
		return "/user/updateUserInfo";
	}


	//进行实名认证
	@RequestMapping("/addIdentityId")
	public String addIdentityId(User user,HttpServletRequest request,
								@RequestParam(value ="attachs", required = false) MultipartFile[] attachs) throws IOException {
		System.out.println("进入添加文件保存页面--------------------------------------------->");

		BASE64Encoder base64Encoder=null;
		MultipartFile attach = attachs[0];
		base64Encoder = new BASE64Encoder();
		String base64EncoderImg = attach.getOriginalFilename() + "," + base64Encoder.encode(attach.getBytes());
		String json=Idcard.identityId(base64EncoderImg);
	/*	JSONObject jbo = JSON.parseObject(json);*/
/*		System.out.println(jbo.toString());
		System.out.println("返回的正确json"+json);*/
		/*Map mapType = JSON.parseObject(json,Map.class);
		System.out.println("这个是用JSON类,指定解析类型，来解析JSON字符串!!!");
		for (Object obj : mapType.keySet()){
			System.out.println("key为："+obj+"值为："+mapType.get(obj));
			if(obj.equals("words_result")){
				System.out.println("这个是用JSON类,指定解析类型，来解析JSON字符串!!!"+mapType.get(obj).toString());
				Map mapType1 = JSON.parseObject(mapType.get(obj).toString(),Map.class);
				for (Object obj1 : mapType.keySet()){
					System.out.println(mapType1.get(obj1));
				}
			}
		}*/

		return "/user";
	}

/*
	public static Map<String, Object> parseJSON2Map(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		for(Object k : json.keySet()){
			Object v = json.get(k);
			if(v instanceof JSONArray){
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				Iterator it = ((JSONArray)v).iterator();
				while(it.hasNext()){
					Object json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
*/




}
