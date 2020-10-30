package com.leyu.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "tb_storage_commodity")
public class StorageCommodity  extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer storageStockId;

    private String source;

    private Integer sourceId;

    private Boolean matchPhone;
    //1有序列号0无序列号
    private Integer commodityMold;

    private Integer commodityId;

    private String commodity;

    private Integer quantity;

    private BigDecimal price;

    private String note;

    @Transient
    private String serials;
    @Transient
    private List<String> serialList;
    @Transient
    private Integer serialMold;

    public StorageCommodity(Integer storageStockId, String source, Integer sourceId,Integer commodityId, String commodity, Integer quantity) {
        this.storageStockId=storageStockId;
        this.source = source;
        this.sourceId = sourceId;
        this.matchPhone = false;
        this.commodityId = commodityId;
        this.commodity = commodity;
        this.quantity = quantity;
    }

    public StorageCommodity(Integer id,Integer storageStockId, String source, Integer sourceId, Boolean matchPhone, Integer commodityId, String commodity, Integer quantity, BigDecimal price, String note) {
        this.id = id;
        this.storageStockId=storageStockId;
        this.source = source;
        this.sourceId = sourceId;
        this.matchPhone = matchPhone;
        this.commodityId = commodityId;
        this.commodity = commodity;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
    }

    public StorageCommodity() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Boolean getMatchPhone() {
        return matchPhone;
    }

    public void setMatchPhone(Boolean matchPhone) {
        this.matchPhone = matchPhone;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getStorageStockId() {
        return storageStockId;
    }

    public void setStorageStockId(Integer storageStockId) {
        this.storageStockId = storageStockId;
    }

    public String getSerials() {
        return serials;
    }

    public void setSerials(String serials) {
        this.serials = serials;
    }

    public Integer getCommodityMold() {
        return commodityMold;
    }

    public void setCommodityMold(Integer commodityMold) {
        this.commodityMold = commodityMold;
    }

    public Integer getSerialMold() {
        return serialMold;
    }

    public void setSerialMold(Integer serialMold) {
        this.serialMold = serialMold;
    }

    public List<String> getSerialList() {
        return serialList;
    }

    public void setSerialList(List<String> serialList) {
        this.serialList = serialList;
    }
}