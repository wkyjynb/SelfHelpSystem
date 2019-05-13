package com.t226.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.t226.tools.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.t226.pojo.User;
/**
 * 拦截器
 * @author LXW
 *
 */
public class SysInterceptor extends HandlerInterceptorAdapter{

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)throws Exception {
		System.out.println("进入拦截器------------------------------------------------>>>>>>>>>>>>>");
		HttpSession session=request.getSession();
			User user=(User)session.getAttribute(Constants.USER_SESSION);
			if(user==null){
				response.sendRedirect(request.getContextPath()+"/403.jsp");
			return false;
			}
			return true;
	}
}
