package com.t226.service.user;

import java.util.List;

import com.t226.pojo.Detection;
import com.t226.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
	void affairAddUser(User user);//添加用户
	public User userLogin(String userCode,String userPassword);//用户登录
	public User dologin(String userCode,String userPassword);//管理员登陆
	public boolean doInsert(Detection detection);//添加设备信息
	public List<Detection> finSelect();//查看设备信息
	public List<Object> finCount(String date,String date1);//模糊查询设备信息
	int addIdentity(User user);//进行身份认证
	User wxUser(@Param("wxId") String wxId);	//微信登录或注册
	int wxBind(@Param("wxId") String wxId,@Param("userId") int userId);//微信绑定
	int updateMoney(int money,int id);//充值
	int addEmail(String email,int id);//绑定邮箱
	User modifyPhone(String phone);//根据手机号码获取邮箱
	int modifyPwd(String pwd,String phone);//修改密码
}
