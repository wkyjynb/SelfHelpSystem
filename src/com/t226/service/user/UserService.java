package com.t226.service.user;

import java.util.List;

import com.t226.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
	void affairAddUser(User user);//添加用户
	User userLogin(String phone,String password);//用户登录
	int addIdentity(User user);//进行身份认证
	User wxUser(@Param("wxId") String wxId);	//微信登录或注册
	int wxBind(@Param("wxId") String wxId,@Param("userId") int userId);//微信绑定
	int updateMoney(int money,int id);//充值
}
