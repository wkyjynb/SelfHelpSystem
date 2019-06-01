package com.t226.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.t226.dao.user.UserMapper;
import com.t226.pojo.Detection;
import com.t226.pojo.User;
import com.t226.service.user.UserService;
import com.t226.tools.Constants;
import com.t226.tools.Mail;
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
import java.security.GeneralSecurityException;
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
		session.setAttribute(Constants.USER_SESSION,userService.phoneLogin(user.getPhone()));
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
	//进入管理员用户登陆页
	@RequestMapping("/index.html")
	public String index(){
		return "index";
	}
	//进入管理员登陆页面
	@RequestMapping("/backendLogin.html")
	public String backendLogin(){
		return  "backendlogin";
	}
	//管理员登陆操作
	@RequestMapping("dologin.html")
	public String dologin(String userCode,String userPassword,HttpSession session) {
		User user = userService.dologin(userCode, userPassword);
		if (null == user) {
			return "redirect:/backendLogin.html";
		} else {
			session.setAttribute(Constants.USER_SESSION, user);
			return "user/appversionadd";
		}
	}
	@RequestMapping(value="/add.html",method=RequestMethod.POST)
	@ResponseBody
	public Object yanMess() {
		String name=httpGet("http://get.iot.o8y.net/Pro_Get/?type=4&way=value&dev_uid=520922785&api_key=7EE04114A9F6C2FA9DEA68F7586BDFE3&sel_io=IO01");
		String name2=httpGet("http://get.iot.o8y.net/Pro_Get/?type=4&way=value&dev_uid=520922785&api_key=7EE04114A9F6C2FA9DEA68F7586BDFE3&sel_io=IO03");
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Detection detection=new Detection();
		detection.setHumidity(Integer.parseInt(name.substring(2,4)));
		detection.setTemperature(Integer.parseInt(name.substring(0, 2)));
		detection.setSmoke(Integer.parseInt(name2));
		detection.setDate(date);
		int i=1;
		if(userService.doInsert(detection)) {
			System.out.println("添加成功");
		}
		if(Integer.parseInt(name2)==1) {
			i=1;
		}
		if(Integer.parseInt(name2)==0&&i==1) {
			sms();
			i--;
		}
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("temperature", name.substring(0, 2));
		map.put("humidity", name.substring(2,4));
		map.put("smoke",Integer.parseInt(name2));
		map.put("date", dateFormat.format(date));
		return JSONArray.toJSONString(map);
	}
	@RequestMapping(value="/sel.html",method=RequestMethod.POST)
	@ResponseBody
	public Object selYan() {
		List<Detection> list=userService.finSelect();
		return JSONArray.toJSONString(list);
	}
	@RequestMapping(value="date.html",method=RequestMethod.POST)
	@ResponseBody
	public Object getDate(String date,String date1) {
		System.out.println("-----------------------------进入");
		List<Object> list=userService.finCount(date, date1);
		System.out.println(JSONArray.toJSONString(list));
		return JSONArray.toJSONString(list);
	}
	public String httpGet(String url) {
		// get请求返回结果
		String str = null;
		System.out.println(1111);
		try {
			System.out.println(2222);
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity());
				str= new String(strResult.getBytes("ISO-8859-1"), "UTF-8");
				/** 把json字符串转换成json对象 **/
			} else {
				System.out.println("读取数据失败..");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	public void sms() {
		HashMap<String, Object> result = null;
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init("app.cloopen.com", "8883");
		restAPI.setAccount("8aaf07086ab0c082016ab53d334d0306", "f7d91887431242968258a0a1d613c820");
		restAPI.setAppId("8aaf07086ab0c082016ab53d33a7030d");
		result = restAPI.sendTemplateSMS("18942587389","1" ,new String[]{"检测到火灾，请管理员拨打119，管理员请立马疏散人员"});//免费只能给注册的号码发送
		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}
	//进入修改密码界面
	@RequestMapping(value = "modify.html",method = RequestMethod.GET)
	public String modify(){
		return "/user/ModifyPwd";
	}
	@RequestMapping(value = "modifyPhone.html",method = RequestMethod.POST)
	@ResponseBody
	public Object modifyPhone(String phone){
		User user=(User)userService.modifyPhone(phone);
		int mail = 0;
		if(user!=null) {
			try {
				mail = Mail.mail(user.getEmail());
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("yan",mail);
		return JSONArray.toJSONString(map);
	}
	@RequestMapping(value="/extits.html",method = RequestMethod.POST)
	@ResponseBody
	public Object extits(String phone){
		Map<String,Object> map=new HashMap<String,Object>();
		User user=(User)userService.modifyPhone(phone);
		if(user!=null) {
			Jedis jedis = new Jedis("106.12.129.166", 6379);
			jedis.auth("1234");
			map.put("zhen", jedis.smembers(user.getEmail()));
		}
		else {
			map.put("zhen", "0");
		}
		return JSONArray.toJSONString(map);
	}
	@RequestMapping(value = "modifyPwd.html",method = RequestMethod.POST)
	public String modifyPwd(String phone,String pwd){
		int result=userService.modifyPwd(pwd,phone);
		if(result>0){
			return "redirect:/";
		}
		return "redirect:/modify.html";
	}
}