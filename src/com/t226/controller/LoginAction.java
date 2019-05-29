package com.t226.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.t226.pojo.User;
import com.t226.service.user.UserService;
import com.t226.tools.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.http.impl.client.DefaultHttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/WeCat")

public class LoginAction {

	@Resource
	private UserService userService;

	private static final String localhost="http://399cbdca.ngrok.io";

	@RequestMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("=======进入login=========>>");
		String url = "https://open.weixin.qq.com/connect/qrconnect?";
		url += "appid=wx860bf23c66d93e33";
		url += "&redirect_uri=" + URLEncoder.encode(localhost+"/SelfHelpSystem/WeCat/callBackLogin", "GBK");
		url += "&response_type=code&scope=snsapi_login";
		url += "&state=" + request.getSession().getId() + "#wechat_redirect";
		System.out.println("url>>>" + url);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/callBackLogin")
	public String callBackLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("callBackLogin....");
		// return "redirect:../loginSuccess.jsp";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		System.out.println("code=" + code);
		System.out.println("state=" + state);
		// 获得access_token数据，获得访问令牌。等下要通过令牌去获得用户的信息
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
		url += "appid=wx860bf23c66d93e33";
		url += "&secret=9c92026ab4faa4a4f7ac4cf10b2a8a3c";
		url += "&code=" + code + "&grant_type=authorization_code";
		// 要去执行这个URL，并通过这个URL获得返回值
		JSONObject jsonObject = this.httpGet(url);

		System.out.println(jsonObject.toString());
		String at = jsonObject.getString("access_token");
		String openId = jsonObject.getString("openid");
		System.out.println("at="+at);
		url="https://api.weixin.qq.com/sns/userinfo?access_token="+at+"&openid="+openId;
		jsonObject = this.httpGet(url);
		addWxUser(jsonObject.getString("nickname"),request);
		return "redirect:/user/userMain.html";
	}

	/**
	 * 发送get请求 http://www.cnblogs.com/QQParadise/articles/5020215.html
	 *
	 * @param url
	 *            路径
	 * @return
	 */
	public JSONObject httpGet(String url) {
		// get请求返回结果
		JSONObject jsonResult = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity());
				System.out.println("strResult..." + strResult);
				String str= new String(strResult.getBytes("ISO-8859-1"), "UTF-8");
				System.out.println("str..." + str);
				/** 把json字符串转换成json对象 **/
				jsonResult = JSON.parseObject(str);

				System.out.println("strResult=" + str);
			} else {
				System.out.println("读取数据失败..");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}



	//微信用户登录或者注册
	public void addWxUser(String str,HttpServletRequest request){
		//System.out.println(jsonObject.getString("openid"));
		request.getSession().setAttribute(Constants.USER_SESSION, userService.wxUser(str));
	}


















	//绑定微信
	@RequestMapping(value = "/login1")
	public void login1(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("=======进入login=========>>");
		String url = "https://open.weixin.qq.com/connect/qrconnect?";
		url += "appid=wx860bf23c66d93e33";
		url += "&redirect_uri=" + URLEncoder.encode(localhost+"/SelfHelpSystem/WeCat/callBackLogin1", "GBK");
		url += "&response_type=code&scope=snsapi_login";
		url += "&state=" + request.getSession().getId() + "#wechat_redirect";
		System.out.println("url>>>" + url);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//绑定微信
	@RequestMapping(value = "/callBackLogin1")
	public String callBackLogin1(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("callBackLogin....");
		// return "redirect:../loginSuccess.jsp";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		System.out.println("code=" + code);
		System.out.println("state=" + state);
		// 获得access_token数据，获得访问令牌。等下要通过令牌去获得用户的信息
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
		url += "appid=wx860bf23c66d93e33";
		url += "&secret=9c92026ab4faa4a4f7ac4cf10b2a8a3c";
		url += "&code=" + code + "&grant_type=authorization_code";
		// 要去执行这个URL，并通过这个URL获得返回值
		JSONObject jsonObject = this.httpGet(url);

		System.out.println(jsonObject.toString());
		String at = jsonObject.getString("access_token");
		String openId = jsonObject.getString("openid");
		System.out.println("at="+at);
		url="https://api.weixin.qq.com/sns/userinfo?access_token="+at+"&openid="+openId;
		jsonObject = this.httpGet(url);
		User user=(User)request.getSession().getAttribute(Constants.USER_SESSION);
		user.setWxId(jsonObject.getString("nickname"));
		userService.wxBind(user.getWxId(),user.getId());//进行绑定微信
		request.getSession().setAttribute(Constants.USER_SESSION,user);
		return "redirect:/user/userMain.html";
	}











}
// The ends;
