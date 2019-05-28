package com.t226.dao.user;

import com.t226.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	void affairAddUser(User user);//添加用户
	User userLogin(@Param("phone") String phone);//用户登录
	int addIdentity(User user);//进行身份认证
	User wxUser(@Param("wxId") String wxId);//微信登录或注册
	int addWxUser(@Param("wxId") String wxId);//微信注册
	int wxBind(@Param("wxId") String wxId,@Param("userId") int userId);//微信绑定
	int updateMoney(@Param("money") int money,@Param("id") int id);//充值
}
