package com.leyu.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_city")
public class City  extends BasePojo  implements java.io.Serializable {

    @Id
    private Integer id;

    private String name;

    private String fullName;

    private String acronym;

    private Integer pid;

    private Integer grade;

    private Integer isDel;

    private String zipCode;

    private String areaCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", fullName=").append(fullName);
        sb.append(", acronym=").append(acronym);
        sb.append(", pid=").append(pid);
        sb.append(", grade=").append(grade);
        sb.append(", isDel=").append(isDel);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", areaCode=").append(areaCode);
        sb.append("]");
        return sb.toString();
    }
}