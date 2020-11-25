package com.leyu.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "tb_storage_serial")
public class StorageSerial  extends BasePojo implements java.io.Serializable{
    @Id
    private Integer id;

    private Integer storageStockId;

    private Integer mold;

    private String serial;

    private Integer quantity;

    private Integer freeze;

    private Integer status;

    private Integer inStorageCommodityId;

    private Integer outStorageCommodityId;

    @Transient
    private String corpName;
    @Transient
    private String commodity;
    @Transient
    private String store;

    public StorageSerial(Integer storageStockId, Integer mold, String serial, Integer quantity, Integer freeze, Integer status, Integer inStorageCommodityId) {
        this.storageStockId = storageStockId;
        this.mold = mold;
        this.serial = serial;
        this.quantity = quantity;
        this.freeze = freeze;
        this.status = status;
        this.inStorageCommodityId = inStorageCommodityId;
    }

    public StorageSerial(Integer id, Integer storageStockId, Integer mold, String serial, Integer quantity, Integer freeze, Integer status, Integer inStorageCommodityId, Integer outStorageCommodityId) {
        this.id = id;
        this.storageStockId = storageStockId;
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

    public Integer getStorageStockId() {
        return storageStockId;
    }

    public void setStorageStockId(Integer storageStockId) {
        this.storageStockId = storageStockId;
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

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}