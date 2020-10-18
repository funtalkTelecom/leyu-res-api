package com.leyu.controller;

import com.leyu.dto.Result;
import com.leyu.pojo.Permission;
import com.leyu.pojo.Role;
import com.leyu.service.RoleRightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/roleright")
public class RoleRightController {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleRightService roleRightService;

    @RequestMapping("/rightlist")
    public Result getRightList(@RequestBody Permission permission) {

        Result  rightResult= roleRightService.getRightList(permission);
        return  rightResult;
    }

    @RequestMapping("/allright")
    public Result getAllRight(){

        Result  rightResult= roleRightService.getAllRight();
        return  rightResult;
    }

    @RequestMapping("/rolelist")
    public Result getRoleList(@RequestBody Role role) {

        Result rightResult= roleRightService.getRoleList(role);
        return  rightResult;
    }

    @RequestMapping("/allrole")
    public Result getAllRole(){

        Result  rightResult= roleRightService.getAllRole();
        return  rightResult;
    }


    @DeleteMapping("/delete/{roleid}/{rolerightid}")
    public Result deleteRoleRight(@PathVariable("roleid") Integer roleId,
                                  @PathVariable("rolerightid") Integer[] roleRightId){

        Result result= roleRightService.deleteRoleRight(roleRightId);
        if (result.isSuccess()) result= roleRightService.getRoleById(roleId);
        return result;

    }

    @RequestMapping(value = "/addrole")
    public Result  addRole(@RequestBody Role role){

        Result result= roleRightService.addRole(role);
        return result;

    }

    @PutMapping(value = "/setrole/{roleId}/{rightIds}")
    public Result  setRole(@PathVariable("roleId") Integer roleId,
                                    @PathVariable("rightIds") Integer[] rightIds){

        Result result= roleRightService.setRoleRight(roleId,rightIds);
        if (result.isSuccess()) result= roleRightService.getRoleById(roleId);
        return result;

    }

    @DeleteMapping("/deleterole/{id}")
    public Result deleteRole(@PathVariable("id") Integer roleId){

        return  roleRightService.deleteRole(roleId);

    }


}
