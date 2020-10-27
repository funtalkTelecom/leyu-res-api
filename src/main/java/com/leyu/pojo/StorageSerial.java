package com.leyu.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_storage_serial")
public class StorageSerial  extends BasePojo implements java.io.Serializable{
    @Id
    private Integer id;

    private Integer storageStoreId;

    private Integer mold;

    private String serial;

    private Integer quantity;

    private Integer freeze;

    private Integer status;

    private Integer inStorageCommodityId;

    private Integer outStorageCommodityId;

    public StorageSerial(Integer id, Integer storageStoreId, Integer mold, String serial, Integer quantity, Integer freeze, Integer status, Integer inStorageCommodityId, Integer outStorageCommodityId) {
        this.id = id;
        this.storageStoreId = storageStoreId;
        this.mold = mold;
        this.serial = serial;
        this.quantity = quantity;
        this.freeze = freeze;
        this.status = status;
        this.inStorageCommodityId = inStorageCommodityId;
        this.outStorageCommodityId = outStorageCommodityId;
    }

    public StorageSerial() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStorageStoreId() {
        return storageStoreId;
    }

    public void setStorageStoreId(Integer storageStoreId) {
        this.storageStoreId = storageStoreId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial == null ? null : serial.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getInStorageCommodityId() {
        return inStorageCommodityId;
    }

    public void setInStorageCommodityId(Integer inStorageCommodityId) {
        this.inStorageCommodityId = inStorageCommodityId;
    }

    public Integer getOutStorageCommodityId() {
        return outStorageCommodityId;
    }

    public void setOutStorageCommodityId(Integer outStorageCommodityId) {
        this.outStorageCommodityId = outStorageCommodityId;
    }

    public Integer getMold() {
        return mold;
    }

    public void setMold(Integer mold) {
        this.mold = mold;
    }

    public Integer getFreeze() {
        return freeze;
    }

    public void setFreeze(Integer freeze) {
        this.freeze = freeze;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}