package com.leyu.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_storage_commodity_phone")
public class StorageCommodityPhone  extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer storageCommodityId;

    private String phone;

    private Integer serialId;

    public StorageCommodityPhone(Integer id, Integer storageCommodityId, String phone, Integer serialId) {
        this.id = id;
        this.storageCommodityId = storageCommodityId;
        this.phone = phone;
        this.serialId = serialId;
    }

    public StorageCommodityPhone() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStorageCommodityId() {
        return storageCommodityId;
    }

    public void setStorageCommodityId(Integer storageCommodityId) {
        this.storageCommodityId = storageCommodityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }
}