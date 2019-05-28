package com.t226.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.t226.dao.user.UserMapper;
import com.t226.pojo.Detection;
import com.t226.pojo.User;
import com.t226.service.user.UserService;
import com.t226.tools.Constants;
import com.t226.tools.Sms;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import org.apache.http.HttpResponse;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import java.util.logging.Logger;

/**
 * 用户未登录时页面
 */
@Controller
public class UserLoginController {


	Jedis jedis=new Jedis("106.12.119.87",6379);

	@Resource
	private UserService userService;

	public String[] fsd=new String[]{"s","d"};
	//进入登入页面
	@RequestMapping(value ={"/userLogin.html","/"})
	public String userLogin(){
		return "userLogin";
	}

	//进入注册页面
	@RequestMapping("/Register.html")
	public String userRegister(){
		return "Register";
	}

	//用户注册
	@RequestMapping("/registerSave")
	public String registerSave(User user, HttpSession session){
		userService.affairAddUser(user);
		session.setAttribute(Constants.USER_SESSION,user);
		return "redirect:/user/userMain.html";
	}

	//用户登录操作
	@RequestMapping("/userLogin")
	public String userLogin(String phone,String password,HttpSession session){
		User user=userService.userLogin(phone,password);

		if(null==user){
			return "redirect:/userLogin.html";
		}else{
			session.setAttribute(Constants.USER_SESSION,user);
			return "redirect:/user/userMain.html";
		}
	}

	//短信服务
	@ResponseBody
	@RequestMapping(value = "/getPhone",produces = "text/html;charset=UTF-8")
	public Object getPhone(String phone){
		jedis.select(0);
		if(StringUtils.isNullOrEmpty(jedis.get(phone))){
			System.out.println("发送了手机号");
			int random=new Random().nextInt(8900)+1000;//产生四位随机数
			Sms.sendSms(String.valueOf(random));//发送验证码
			jedis.setex(phone,60,String.valueOf(random));
			return "{\"name\":\"发送成功\"}";
		}else{
			return "{\"name\":\"请在"+jedis.ttl(phone)+"秒后发送\"}";
		}
	}

	//验证码是否过期
	@ResponseBody
	@RequestMapping(value="/getVerification")
	public Object getVerification(String phone,String verification){
		if(StringUtils.isNullOrEmpty(jedis.get(phone))||!jedis.get(phone).equals(verification)){
			return "{\"name\":\"no\"}";
		}else{
			return "{\"name\":\"ok\"}";
		}

	}




}