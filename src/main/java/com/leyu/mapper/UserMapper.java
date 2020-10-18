package com.leyu.mapper;

import com.github.abel533.mapper.Mapper;
import com.leyu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper  extends Mapper<User>,BaseMapper<User>{

    public List<Map>  getPower(Integer id);
    public List<String> findRoles(Integer id);
    List<Map> finRolesByUserId(Integer id);
    void deleteRoleByUserId(@Param("userId") Integer userId);
    void insertUserRole(@Param("userId") Integer userId,@Param("userRoleIdArray") Integer[] userRoleIdArray);
}