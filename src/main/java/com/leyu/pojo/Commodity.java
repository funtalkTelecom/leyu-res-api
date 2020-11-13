package com.leyu.pojo;

import com.leyu.config.validator.ValidateGroup;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "tb_commodity")
public class Commodity extends BasePojo implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**归属运营商*/
//    @NotNull(message = "请选择运营商",groups ={ValidateGroup.FirstGroup.class})
    private Integer corpId;
    @Transient
    private String corpIdStr;
    /**分类(1sim卡2常规商品)*/
    @Range(min = 1, max = 2,message = "请选择商品的分类",groups ={ValidateGroup.FirstGroup.class})
    private Integer classify;
    /**品名*/
    @NotNull(message = "请填写品名",groups ={ValidateGroup.FirstGroup.class})
    private String name;
    /**号段(-1代表不限)*/
    @Min(value=-1,message = "请选择商品号段",groups ={ValidateGroup.FirstGroup.class})
    private Integer sectionNo;
    /**地市(-1代表不限)*/
    @Min(value=-1,message = "请选择号段地市",groups ={ValidateGroup.FirstGroup.class})
    @NotNull(message = "请选择号段地市",groups ={ValidateGroup.FirstGroup.class})
    private Integer sectionCity;
    @Transient
    private String sectionCityStr;
    /**序列管理(1是0否)*/
    @Range(min = 0, max = 1,message = "请选择序列管理模式",groups ={ValidateGroup.FirstGroup.class})
    private Integer serial;
    /**备注*/
    private String remark;
    /**状态(1有效2无效)*/
    private Integer status;
    /**删除(0未删1删除)*/
    private Boolean isDel;
    /***/
    private Integer addUser;
    /***/
    private Date addDate;

    public Commodity(){

    }
    public Commodity(Integer corpId, Integer classify, String name, Integer sectionNo, Integer sectionCity, Integer serial, String remark, Integer status, Boolean isDel, Integer addUser, Date addDate) {
        this.corpId = corpId;
        this.classify = classify;
        this.name = name;
        this.sectionNo = sectionNo;
        this.sectionCity = sectionCity;
        this.serial = serial;
        this.remark = remark;
        this.status = status;
        this.isDel = isDel;
        this.addUser = addUser;
        this.addDate = addDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(Integer sectionNo) {
        this.sectionNo = sectionNo;
    }

    public Integer getSectionCity() {
        return sectionCity;
    }

    public void setSectionCity(Integer sectionCity) {
        this.sectionCity = sectionCity;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }

    public Integer getAddUser() {
        return addUser;
    }

    public void setAddUser(Integer addUser) {
        this.addUser = addUser;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getSectionCityStr() {
        return sectionCityStr;
    }

    public void setSectionCityStr(String sectionCityStr) {
        this.sectionCityStr = sectionCityStr;
    }

    public String getCorpIdStr() {
        return corpIdStr;
    }

    public void setCorpIdStr(String corpIdStr) {
        this.corpIdStr = corpIdStr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", corpId=").append(corpId);
        sb.append(", classify=").append(classify);
        sb.append(", name=").append(name);
        sb.append(", sectionNo=").append(sectionNo);
        sb.append(", sectionCity=").append(sectionCity);
        sb.append(", serial=").append(serial);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", isDel=").append(isDel);
        sb.append(", addUser=").append(addUser);
        sb.append(", addDate=").append(addDate);
        sb.append("]");
        return sb.toString();
    }
}