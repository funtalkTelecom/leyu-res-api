package com.leyu.controller;

import com.leyu.config.annotation.Powers;
import com.leyu.dto.Result;
import com.leyu.pojo.User;
import com.leyu.service.RoleRightService;
import com.leyu.service.UserService;
import com.leyu.utils.PowerConsts;
import com.leyu.utils.ReqLimitUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/user")
public class UserController {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private UserService userService;
	@Autowired private RoleRightService roleRightService;

	public static String RANK_AUTH_CODE = "rankAuthCode";

	@RequestMapping("/login")
	@Powers({PowerConsts.NOLOGINPOWER})
	public Result login(@RequestBody User user) throws IOException,
			NoSuchAlgorithmException {

		Result result;
		int limitResult = ReqLimitUtils.residualReqNum("login",
				new ReqLimitUtils.ReqLimit("yes", "login", 1L, 6, 0L));

		if (limitResult <= 0) {
			return new Result(Result.WARN, "登录频繁,已限制登录,请稍后再试");
		}

		if (StringUtils.isBlank(user.getLoginName())) return new Result(Result.WARN, "用户名为空!");
		if (StringUtils.isBlank(user.getPassword())) return new Result(Result.WARN, "密码为空!");

		result = userService.login(user.getLoginName(), user.getPassword());

		return result;

	}

	@RequestMapping("/logout")
	@Powers({PowerConsts.NOLOGINPOWER})
	public Result logOut(@RequestHeader("token") String token) {

		Result  result = userService.logout(token);
		return result;

	}

	@RequestMapping("/list")
	public Result getUserList(@RequestBody User user) {

		Result userListResult= userService.pageUser(user);
		return  userListResult;
	}

	@PutMapping(value = "/{id}/status/{status}")
	public Result  changeUserStatus(@PathVariable("id") Integer userId, @PathVariable("status") String userStatus){

		return userService.changeUserStatus(userId,userStatus);

	}

	@RequestMapping("/addOrEdit")
	public Result addOrEdit(@RequestBody User user){

		if (user.getId()==null)
		return userService.addUser(user);
		else
			return userService.editUser(user);

	}

	@RequestMapping("/checkLoginName")
	public Result checkLoginName(String  loginName){
		return userService.checkLoginName(loginName);

	}

	@DeleteMapping("/delete/{id}")
	public Result deleteUser(@PathVariable("id") Integer userId){

		return  userService.deleteUser(userId);

	}

	/**
	 * 临时 查询 类型是“卡商”的公司
	 * @return
	 */
	@RequestMapping("/corp/card")
	public Result corpCard(){
		Object object=userService.queryCorpList();
		return new Result(Result.OK,object);
	}

	/**
	 * 临时 查询 与当前公司签约的运营商
	 * @return
	 */
	@RequestMapping("/corp/tele")
	public Result corpTele(){
		Object object=userService.queryCorpList();
		return new Result(Result.OK,object);
	}


}
