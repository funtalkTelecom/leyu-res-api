package com.leyu.pojo;

import com.leyu.config.validator.ValidateGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "tb_corp_address")
public class CorpAddress extends BasePojo implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer corpId;
    @NotNull(message = "请填写收件人",groups ={ValidateGroup.FirstGroup.class})
    private String personName;
    @NotNull(message = "请填写收件人电话",groups ={ValidateGroup.FirstGroup.class})
    private String personTel;
    @NotNull(message = "请选择所在区域",groups ={ValidateGroup.FirstGroup.class})
    private Integer province;
    @NotNull(message = "请选择所在区域",groups ={ValidateGroup.FirstGroup.class})
    private Integer city;
    @NotNull(message = "请填写所在区域",groups ={ValidateGroup.FirstGroup.class})
    private Integer district;
    @NotNull(message = "请填写街道地址",groups ={ValidateGroup.FirstGroup.class})
    private String address;

    private Date createDate;

    private Date updateDate;

    private Integer adduser;

    private Boolean isDel;

    private Boolean isDefault;

    private String note;

    @Transient
    private String provinceStr;
    @Transient
    private String cityStr;
    @Transient
    private String districtStr;
    @Transient
    private String fullAddress;

    public CorpAddress() {
    }

    public CorpAddress(Integer corpId, String personName, String personTel, Integer province, Integer city, Integer district, String address, Integer adduser) {
        this.corpId = corpId;
        this.personName = personName;
        this.personTel = personTel;
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
        this.createDate = new Date();
        this.updateDate = new Date();
        this.adduser = adduser;
        this.isDel = false;
        this.isDefault = false;
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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonTel() {
        return personTel;
    }

    public void setPersonTel(String personTel) {
        this.personTel = personTel;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getAdduser() {
        return adduser;
    }

    public void setAdduser(Integer adduser) {
        this.adduser = adduser;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getDistrictStr() {
        return districtStr;
    }

    public void setDistrictStr(String districtStr) {
        this.districtStr = districtStr;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", corpId=").append(corpId);
        sb.append(", personName=").append(personName);
        sb.append(", personTel=").append(personTel);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", address=").append(address);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", adduser=").append(adduser);
        sb.append(", isDel=").append(isDel);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", note=").append(note);
        sb.append("]");
        return sb.toString();
    }
}