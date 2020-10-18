package com.leyu.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.utils.*;
import com.leyu.mapper.UserMapper;
import com.leyu.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserService extends BaseService {

	private static Logger logger = LogManager.getLogger(UserService.class);

	@Autowired SessionUtil sessionUtil;
	@Autowired private UserMapper userMapper;
	@Autowired private ApiSessionUtil apiSessionUtil;
	@Autowired private RoleRightService roleRightService;

	public void test1(int i) {
		User u = new User(i);
		userMapper.insert(u);
//		if(i==16) throw  new ServiceException("test");
	}
	public void test() {
		User u = new User(100);
		userMapper.insert(u);
//		try{
//			userService.paytest();
//		} catch (Exception e) {
//			System.out.println(e.getMessage()+"----------捕捉异常");
//		}
//	    Example example = new Example(FundOrder.class);
//	    example.createCriteria();
//	    List<User> u = userMapper.selectByExample(new Example(User.class).createCriteria().andEqualTo("id", 1));
//        System.out.println(u.get(0).getLoginName());
//		System.out.println("test begain--------------------");
//		userMapper.test();
//		System.out.println("test end--------------------");
//        List allImeis = new ArrayList();
//        List imeis = Arrays.asList(new String[]{"A1234","B1234"});
//        allImeis.add(CommonMap.create("iccids",imeis).put("itemId", "111111").getData());
//        allImeis.add(CommonMap.create("iccids",Arrays.asList(new String[]{"C1234","D1234"})).put("itemId", "222222").getData());
//        System.out.println(iccidMapper.batchInsertTemp(allImeis, 1111111l));
//		User u1 = new User(11l);
//		u1.setLoginName("111");
//		userMapper.insert(u1);
//		System.out.println("aaaaaaaaaaaaaaaa");
	}

	public Result pageUser(User user) {

		PageHelper.startPage(user.getStart(),user.getLimit());
		Page<Object> ob=userMapper.queryPageList(user);
		PageInfo<Object> pm = new PageInfo<Object>(ob);
		return new Result(Result.OK, pm);

	}

	public Result login(String loginName, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		User user = null;
		Map<String,Object> resMap=new HashMap<>();

		User param = new User();
		param.setLoginName(loginName);
		user = userMapper.selectOne(param);

		if(user == null )  return   new Result(Result.WARN,"用户不存在!");

		if(!user.getPassword().equals(DigestUtils.md5Hex(password)))  return   new Result(Result.WARN,"密码错误!");

		if(!"1".equals(user.getStatus())){
		 return  new Result(Result.WARN,"用户被冻结!");

		}

		Map<String,List>  permissionMap = roleRightService.getPermissionByUserId(user.getId());

		user.setPassword("");
		String token=TokenGenerator.generateValue();
		this.apiSessionUtil.saveObject(token,JsonUtil.obj2String(user),7200L);

		resMap.put("token",token);
		resMap.put("user",user);
		resMap.put("menuRules",permissionMap.get("menuPermission"));
		resMap.put("operationRules",permissionMap.get("operationPermisson"));

		return new Result(Result.OK, resMap);
	}


	public Result logout(String token){

       this.apiSessionUtil.delete(token);
       return  new Result(Result.OK,"退出成功");
	}

//    public User getUser(Integer id) {
//		User u = userMapper.selectByPrimaryKey(id);
//		List<Map> list = userMapper.finRolesByUserId(id);
//		String roles="";
//		for (int i = 0; i <list.size(); i++) {
//			Map map=list.get(i);
//			if(map.get("userid")!=null)roles+=map.get("id")+",";
//		}
//		u.setRoles(roles);
//		return u;
//    }

    public Result checkLoginName(String loginName){

		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("loginName", loginName);
		List<User> list =  userMapper.selectByExample(example);
		User u = list.size() == 0 ? null : list.get(0);
		if(u != null && StringUtils.equals(u.getLoginName(),loginName))
			return new Result(Result.WARN,"工号["+loginName+"]已存在!");
		return  new Result(Result.OK,"工号["+loginName+"]可以创建!");

	}

	@Transactional
	public Result addUser(User user){

		userMapper.insertSelective(user);

		if (user.getId() != null){

			userMapper.insertUserRole(user.getId(),user.getRoleIdsSelected());
		   return new Result(Result.OK, "创建成功!");

		}else{

			return new Result(Result.WARN, "创建失败!");
		}

	}

	@Transactional
	public Result editUser(User user){

			User dbUser = userMapper.selectByPrimaryKey(user.getId());
			if(dbUser == null) return new Result(Result.WARN, "用户不存在!");
			int updateRet =userMapper.updateByPrimaryKeySelective(user);

			if (updateRet >0) {

				userMapper.deleteRoleByUserId(user.getId());
                if (user.getRoleIdsSelected().length>0)
				   userMapper.insertUserRole(user.getId(),user.getRoleIdsSelected());
				return new Result(Result.OK, "修改成功!");

			}else{

				return new Result(Result.WARN, "修改失败!");
			}

	}

	public Result deleteUser(Integer userId){

		int deleteRet =userMapper.deleteByPrimaryKey(userId);

		if (deleteRet >0){

			userMapper.deleteRoleByUserId(userId);
			return new Result(Result.OK, "删除成功!");

		}else{

			return new Result(Result.WARN, "删除失败!");
		}

	}

	public Result freezeUser(User user) {
		int status = NumberUtils.toInt(String.valueOf(user.getStatus()));
		if(NumberUtils.toLong(String.valueOf(user.getId())) == 0 || (status !=1 && status != 2)) return new Result(Result.ERROR, "参数异常");
		User updateUser = new User();
		updateUser.setId(user.getId());
		updateUser.setStatus(String.valueOf(user.getStatus()));
		int count = userMapper.updateByPrimaryKeySelective(updateUser);
		if(count != 1) return new Result(Result.ERROR, "提交失败");
		return new Result(Result.OK, "提交成功");
	}

    public Result resetPwd(User user) {
	    user = userMapper.selectByPrimaryKey(user.getId());
	    if(user == null) return new Result(Result.ERROR, "用户不存在");
        String pwd = Utils.randomNoByDateTime(6);
        try {
            user.setPassword(DigestUtils.md5Hex(pwd));
        } catch (Exception e) {
            log.error("",e);
            return new Result(Result.ERROR,"加密异常");
        }
        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count != 1) return new Result(Result.ERROR,"重置失败");

		Map map=new HashMap();
		map.put("loginName", user.getLoginName());
		map.put("pwd", pwd);
		String smsTempUrl = Utils.getSmsBase().getSmsTempUrl();
		Messager.sendSmsTemp(user.getPhone(),map,2007,smsTempUrl);
        return new Result(Result.OK, "重置成功");
    }

    public Result updatePwd(String originPwd, String pwd) {
        User user = userMapper.selectByPrimaryKey(SessionUtil.getUserId());
        if(user == null) return new Result(Result.ERROR, "用户不存在");
        try {
            if(!originPwd.equals(user.getPassword())) return new Result(Result.ERROR, "原始密码错误");
        } catch (Exception e) {
            log.error("",e);
            return new Result(Result.ERROR,"加密异常");
        }
        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count != 1) return new Result(Result.ERROR,"修改密码失败");
        return new Result(Result.OK, "修改密码成功");
    }


	public Result changeUserStatus(Integer userId,String userStatus){

		User user = userMapper.selectByPrimaryKey(userId);
		if(user == null) return new Result(Result.ERROR, "用户不存在!");

		user.setStatus(userStatus);

		int count = userMapper.updateByPrimaryKeySelective(user);
		if(count != 1) return new Result(Result.ERROR,"修改用户状态失败!");
		return new Result(Result.OK, "修改用户状态成功!");

	}


}
