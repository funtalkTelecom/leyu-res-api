package com.leyu.mapper;

import com.github.abel533.mapper.Mapper;
import com.leyu.pojo.UserPermission;
import org.springframework.stereotype.Component;

@Component
public interface UserPermissionMapper extends Mapper<UserPermission>,BaseMapper<UserPermission> {
}