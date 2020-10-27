package com.leyu.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "tb_storage_commodity")
public class StorageCommodity  extends BasePojo implements java.io.Serializable{
    @Id
    private Integer id;

    private String source;

    private Integer sourceId;

    private Boolean matchPhone;

    private Integer commodityId;

    private String commodity;

    private Integer quantity;

    private BigDecimal price;

    private String note;

    public StorageCommodity(Integer id, String source, Integer sourceId, Boolean matchPhone, Integer commodityId, String commodity, Integer quantity, BigDecimal price, String note) {
        this.id = id;
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
}