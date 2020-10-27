package com.leyu.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tb_storage_stock")
public class StorageStock  extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer corpId;

    private String corpName;

    private Integer commodityId;

    private String commodity;

    private Integer storeId;

    private String store;

    private Integer totalQuantity;

    private Integer usableQuantity;

    private Integer freezeQuantity;

    private BigDecimal costPrice;

    private BigDecimal retail1Price;

    private String note;

    private Date addDate;

    private Boolean isDel;

    public StorageStock(Integer id, Integer corpId, String corpName, Integer commodityId, String commodity, Integer storeId, String store, Integer totalQuantity, Integer usableQuantity, Integer freezeQuantity, BigDecimal costPrice, BigDecimal retail1Price, String note, Date addDate, Boolean isDel) {
        this.id = id;
        this.corpId = corpId;
        this.corpName = corpName;
        this.commodityId = commodityId;
        this.commodity = commodity;
        this.storeId = storeId;
        this.store = store;
        this.totalQuantity = totalQuantity;
        this.usableQuantity = usableQuantity;
        this.freezeQuantity = freezeQuantity;
        this.costPrice = costPrice;
        this.retail1Price = retail1Price;
        this.note = note;
        this.addDate = addDate;
        this.isDel = isDel;
    }

    public StorageStock() {
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

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity == null ? null : commodity.trim();
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store == null ? null : store.trim();
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getUsableQuantity() {
        return usableQuantity;
    }

    public void setUsableQuantity(Integer usableQuantity) {
        this.usableQuantity = usableQuantity;
    }

    public Integer getFreezeQuantity() {
        return freezeQuantity;
    }

    public void setFreezeQuantity(Integer freezeQuantity) {
        this.freezeQuantity = freezeQuantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getRetail1Price() {
        return retail1Price;
    }

    public void setRetail1Price(BigDecimal retail1Price) {
        this.retail1Price = retail1Price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
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