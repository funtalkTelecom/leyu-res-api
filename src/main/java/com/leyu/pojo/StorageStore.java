package com.leyu.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_storage_store")
public class StorageStore  extends BasePojo implements java.io.Serializable{
    @Id
    private Integer id;

    private Integer corpId;

    private String name;

    private Integer isDefault;

    private Integer service;

    private String contacts;

    private String phone;

    private String province;

    private String city;

    private String district;

    private Date addDate;

    private Boolean isDel;

    public StorageStore(Integer id, Integer corpId, String name, Integer isDefault, Integer service, String contacts, String phone, String province, String city, String district, Date addDate, Boolean isDel) {
        this.id = id;
        this.corpId = corpId;
        this.name = name;
        this.isDefault = isDefault;
        this.service = service;
        this.contacts = contacts;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.district = district;
        this.addDate = addDate;
        this.isDel = isDel;
    }

    public StorageStore() {
        super();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}