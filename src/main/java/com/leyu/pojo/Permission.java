package com.leyu.pojo;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tb_permission")
public class Permission  extends BasePojo  implements java.io.Serializable {
    private Integer id;

    private Integer type;

    private Integer pid;

    private String name;

    private String code;

    private String icon;

    private String url;

    private Integer seq;

    @Transient
    private List<Permission> children = new ArrayList<Permission>();

    @Transient
    private String includeTopMenu;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<Permission> getChildren() { return children; }

    public void setChildren(List<Permission> children) { this.children = children; }

    public String getIncludeTopMenu() { return includeTopMenu; }

    public void setIncludeTopMenu(String includeTopMenu) {  this.includeTopMenu = includeTopMenu; }

}