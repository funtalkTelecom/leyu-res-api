package com.leyu.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.mapper.RoleMapper;
import com.leyu.mapper.PermissionMapper;
import com.leyu.mapper.UserMapper;
import com.leyu.pojo.Permission;
import com.leyu.pojo.Role;
import com.leyu.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleRightService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private RoleMapper roleMapper;
	@Autowired private PermissionMapper permissionMapper;
	@Autowired private UserMapper userMapper;

	public Result getRightList(Permission permission){

        permission.setIncludeTopMenu("0");

		PageHelper.startPage(permission.getStart(),permission.getLimit());
		List<Permission> list = this.permissionMapper.queryList(permission);
		PageInfo<Permission> pm = new PageInfo<Permission>(list);
		return new Result(Result.OK, pm);

	}

	public Result getAllRight(){

		List<Permission> innerPermissonList = new ArrayList<Permission>();
		List<Permission> rootPermissonList = new ArrayList<Permission>();
		Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();

		List<Permission> basePermissonlist = this.permissionMapper.queryList(null);

		logger.info("-----------"+JsonUtil.obj2String(basePermissonlist));
		for (Permission oneBase : basePermissonlist) {
			// 一级菜单和二级菜单  菜单内部的操作权限
			permissionMap.put(oneBase.getId(), oneBase);
			innerPermissonList.add(oneBase);
		}

		for (Permission oneBase : basePermissonlist) {
			Permission child = oneBase;

			if(child.getPid() == null ){
				rootPermissonList.add(oneBase);
			}else{
				Permission parent = permissionMap.get(child.getPid());
				if (parent!=null)
					parent.getChildren().add(child);
			}
		}

		return new Result(Result.OK, rootPermissonList);
	}

	public Result getRoleList(Role role){

		PageHelper.startPage(role.getStart(),role.getLimit());
		List<Role> retRoleList = this.roleMapper.queryList(role);
		generateRoleRightJsonTree(retRoleList);
		PageInfo<Role> pm = new PageInfo<Role>(retRoleList);
		return new Result(Result.OK, pm);

	}

	public Result getAllRole(){

		List<Role> retRoleList = this.roleMapper.queryList(null);
		return new Result(Result.OK, retRoleList);

	}

	public Result getRoleById(Integer roleId){

		Example example = new Example(Role.class);
		example.createCriteria().andEqualTo("id", roleId);
		List<Role> retRoleList = this.roleMapper.selectByExample(example);

		generateRoleRightJsonTree(retRoleList);
		return new Result(Result.OK, retRoleList);

	}

	public void generateRoleRightJsonTree(List<Role> retRoleList){

		ArrayList<Integer>  roleIdList =new ArrayList<>();
		List<Role> innerRoleList = new ArrayList<>();

		for(Role one: retRoleList)
			roleIdList.add(one.getId());

		List<Role> roleRightList =this.roleMapper.queryRightByRoleId(roleIdList.toArray());

		for(Role oneRetRole: retRoleList){

			innerRoleList.clear();

			for(Role oneRightRole: roleRightList){
				if (Objects.equals(oneRightRole.getId(),oneRetRole.getId()))
					innerRoleList.add(oneRightRole);
			}
			oneRetRole.setChildren(innerRoleList.size() >0 ? getRightByRoleId(innerRoleList): null);
		}
	}


	public List<Role> getRightByRoleId(List<Role> baseRoleList){

		List<Role> innerRoleList = new ArrayList<Role>();
		List<Role> rootRoleList = new ArrayList<Role>();
		Map<Integer,Role> roleMap = new HashMap<Integer,Role>();

		for (Role oneBaseRole : baseRoleList) {
			    // 一级菜单和二级菜单  菜单内部的操作权限
			    roleMap.put(oneBaseRole.getRightId(), oneBaseRole);
			    innerRoleList.add(oneBaseRole);
		}

		for (Role oneBaseRole : baseRoleList) {
			Role child = oneBaseRole;

			if(child.getRightPid() == null ){
				rootRoleList.add(oneBaseRole);
			}else{
				Role parent = roleMap.get(child.getRightPid());
				if (parent!=null)
				   parent.getChildren().add(child);
			}
		}

		return rootRoleList;
	}


	public Map<String,List>  getPermissionByUserId(Integer  userId){

		List<Permission> menuPermissionList = new ArrayList<Permission>();
		List<Permission> rootMenuPermissionList = new ArrayList<Permission>();
		List<String>  operationPermissonList= new ArrayList<String>();

		Map<Integer,Permission> menuPermissionMap = new HashMap<Integer,Permission>();
		Map<String,List> permissionHashMap = new HashMap<String,List>();


		List<Permission>  permissonList = permissionMapper.getPermissionByUserId(userId);


		for (Permission innerOne : permissonList) {

			// 一级菜单和二级菜单
			if (Arrays.asList(new Integer[]{1,2}).contains(innerOne.getType())){

				menuPermissionMap.put(innerOne.getId(), innerOne);
				menuPermissionList.add(innerOne);
			}
            // 菜单内部的操作权限
			if (innerOne.getType()==3)
				operationPermissonList.add(innerOne.getCode());
		}

		for (Permission permission : menuPermissionList) {
			Permission child = permission; //假设为子菜单

			if(child.getPid() == null ){
				rootMenuPermissionList.add(permission);
			}else{
				//父节点
				Permission parent = menuPermissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}

		permissionHashMap.put("menuPermission",rootMenuPermissionList);
		permissionHashMap.put("operationPermisson",operationPermissonList);

		return  permissionHashMap;

	}


	@Transactional
	public Result deleteRole(Integer roleId){

		roleMapper.delete(new Role(roleId));
        roleMapper.deleteRoleRightByRoleId(roleId);
		return new Result(Result.OK, "删除成功!");

	}

	public Result deleteRoleRight(Integer[] roleRightId){

		int deleteRet =roleMapper.deleteRoleRightByIdArray(roleRightId);

		if (deleteRet >0)
			return new Result(Result.OK, "删除成功!");
		else
			return new Result(Result.WARN, "删除失败!");

	}

	@Transactional
	public Result setRoleRight(Integer roleId,Integer[] rightId){

		roleMapper.deleteRoleRightByRoleId(roleId);
		roleMapper.insertBatchRoleRight(roleId,rightId);
		return new Result(Result.OK, "配置成功!");

	}

	@Transactional
	public Result addRole(Role role){

		roleMapper.insertSelective(role);
		logger.info("------------>"+role.getId());
		if (role.getId() !=null)
		   roleMapper.insertBatchRoleRight(role.getId(),role.getRightIdsSelected());
		return new Result(Result.OK, "添加成功!");

	}


	

}
