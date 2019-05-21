package com.t226.service.user;

import java.util.List;

import com.t226.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
	void affairAddUser(User user);//添加用户
	User userLogin(String phone,String password);//用户登录

}
