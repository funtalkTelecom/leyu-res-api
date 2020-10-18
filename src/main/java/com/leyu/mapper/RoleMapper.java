package com.leyu.mapper;

import com.github.abel533.mapper.Mapper;
import com.leyu.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends Mapper<Role>,BaseMapper<Role>  {

    public List<Role> queryRightByRoleId( @Param("roleIdArray") Object[] roleIdArray);

    public  int deleteRoleRightByRoleId(@Param("roleId") Integer roleId);
    public  int deleteRoleRightByIdArray(@Param("roleRightIdArray") Integer[] roleRightIdArray);
    public int insertBatchRoleRight(@Param("roleId") Integer roleId,@Param("roleRightIdArray") Integer[] roleRightIdArray);

}