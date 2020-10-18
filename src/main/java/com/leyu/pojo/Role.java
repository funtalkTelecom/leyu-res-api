package com.leyu.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tb_role")
public class Role  extends BasePojo implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer id;
    private String name;
    private Integer status;
    private String remark;

    @Transient private Integer rightId;
    @Transient private Integer roleRightId;
    @Transient private Integer rightType;
    @Transient private Integer rightPid;
    @Transient private String  rightName;
    @Transient private List<Role> children = new ArrayList<Role>();

    @Transient  private Integer[]  rightIdsSelected;

    public Role() {}
    public Role(Integer id) {
        this.id = id;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name == null ? null : name.trim(); }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark == null ? null : remark.trim(); }

    public Integer getRightId() { return rightId; }

    public void setRightId(Integer rightId) { this.rightId = rightId; }

    public Integer getRoleRightId() { return roleRightId; }

    public void setRoleRightId(Integer roleRightId) { this.roleRightId = roleRightId; }

    public Integer getRightType() { return rightType; }

    public void setRightType(Integer rightType) { this.rightType = rightType; }

    public Integer getRightPid() { return rightPid; }

    public void setRightPid(Integer rightPid) { this.rightPid = rightPid; }

    public String getRightName() { return rightName; }

    public void setRightName(String rightName) { this.rightName = rightName; }

    public List<Role> getChildren() { return children; }

    public void setChildren(List<Role> children) { this.children = children; }

    public Integer[] getRightIdsSelected() { return rightIdsSelected; }

    public void setRightIdsSelected(Integer[] rightIdsSelected) { this.rightIdsSelected = rightIdsSelected; }
}