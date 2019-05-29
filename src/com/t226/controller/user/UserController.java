package com.t226.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.t226.service.building.BuildingService;
import com.t226.service.room.RoomService;
import com.t226.tools.Constants;
import com.t226.tools.Idcard;
import com.t226.tools.Util;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.t226.pojo.User;
import com.t226.service.user.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	//进入邮箱绑定页面
	@RequestMapping("/emailAdd.html")
	public String EmailAdd(){
		return "/user/EmailAdd";
	}
	//进行实名认证
	@RequestMapping("/addIdentityId")
	public String addIdentityId(HttpServletRequest request,HttpSession session,
								@RequestParam(value ="attachs", required = false) MultipartFile[] attachs) throws IOException, ParseException {
		System.out.println("进入添加文件保存页面--------------------------------------------->");

		BASE64Encoder base64Encoder=null;
		MultipartFile attach = attachs[0];
		base64Encoder = new BASE64Encoder();
		String base64EncoderImg = attach.getOriginalFilename() + "," + base64Encoder.encode(attach.getBytes());
		String json=Idcard.identityId(base64EncoderImg);
		System.out.println("返回的正确json"+json);
		JSONObject jsonObject = (JSONObject) JSONObject.parseObject(json);
		if(jsonObject.getString("image_status").equals("normal")){//识别状态
			/**
			 * normal-识别正常
			 reversed_side-未摆正身份证
			 non_idcard-上传的图片中不包含身份证
			 blurred-身份证模糊
			 over_exposure-身份证关键字段反光或过曝
			 unknown-未知状态
			 */
			User user=new User();
			JSONObject jsonObject1 = (JSONObject) JSONObject.parseObject(jsonObject.getString("words_result"));
			JSONObject jsonObject2 = (JSONObject) JSONObject.parseObject(jsonObject1.getString("姓名"));
			JSONObject jsonObject3 = (JSONObject) JSONObject.parseObject(jsonObject1.getString("公民身份号码"));
			JSONObject jsonObject4 = (JSONObject) JSONObject.parseObject(jsonObject1.getString("出生"));
			JSONObject jsonObject5 = (JSONObject) JSONObject.parseObject(jsonObject1.getString("性别"));
			JSONObject jsonObject6 = (JSONObject) JSONObject.parseObject(jsonObject1.getString("民族"));
			user.setUserName(jsonObject2.getString("words"));//姓名
			StringBuffer stringBuilder1=new StringBuffer(jsonObject4.getString("words"));//出生年月
			stringBuilder1.insert(6,"-");
			stringBuilder1.insert(4,"-");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			user.setBirthday(sdf.parse(stringBuilder1.toString()));

			user.setIdentityId(jsonObject3.getString("words"));//身份证号
			String gender=jsonObject5.getString("words");//性别
			if(gender.equals("男")){
				user.setGender(0);
			}else if(gender.equals("女")){
				user.setGender(1);
			}else{
				System.out.println("出错了----------------------->");
			}
			user.setNation(jsonObject6.getString("words"));//民族
			User user1=(User) session.getAttribute(Constants.USER_SESSION);
			user.setId(user1.getId());
			int x=(int)(Math.random()*100);
			user.setCreditScore(580+x);//信用分
			user1.setNation(user.getNation());
			user1.setGender(user.getGender());
			user1.setCreditScore(user.getCreditScore());
			user1.setIdentityId(user.getIdentityId());
			user1.setBirthday(user.getBirthday());
			user1.setUserName(user.getUserName());
			userService.addIdentity(user);

			return "redirect:/user/showUserInfo.html";
		}else {
			request.setAttribute("error","身份证提交不通过,请稍后在试");
			return "/user/updateUserInfo";
		}
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

//充值
@ResponseBody
@RequestMapping("/updateMoney")
public Object updateMoney(int money,HttpSession session){
	User user=(User)session.getAttribute(Constants.USER_SESSION);
int num=userService.updateMoney(money,user.getId());
if(num==1){
	user.setMoney(money+user.getMoney());
	session.setAttribute(Constants.USER_SESSION,user);
}
return "{\"num\":\""+num+"\"}";
}


		}
