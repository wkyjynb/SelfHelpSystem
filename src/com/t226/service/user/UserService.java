package com.t226.service.user;

import java.util.List;

import com.t226.pojo.Detection;
import com.t226.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
	void affairAddUser(User user);//添加用户
	User userLogin(String phone,String password);//用户登录
	User dologin(String userCode,String userPassword);//管理员登陆
	public boolean doInsert(Detection detection);//添加设备信息
	public List<Detection> finSelect();//查看设备信息
	public List<Object> finCount(@Param("date")String date,@Param("date1")String date1);//模糊查询设备信息
}
