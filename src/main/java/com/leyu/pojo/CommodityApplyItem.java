package com.leyu.pojo;

import com.leyu.config.validator.ValidateGroup;
import com.leyu.utils.Arith;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Table(name = "tb_commodity_apply_item")
public class CommodityApplyItem extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer commodityApplyId;
    @NotNull(message = "请选择商品",groups ={ValidateGroup.FirstGroup.class})
    private Integer storageStockId;

    private Integer commodityId;

    private Integer commodityClassify;

    private String commodityName;

    @NotNull(message = "请填写需求数量",groups ={ValidateGroup.FirstGroup.class})
    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal price;

    public CommodityApplyItem(Integer commodityApplyId, Integer storageStockId, Integer commodityId, Integer commodityClassify, String commodityName, Integer quantity,BigDecimal unitPrice) {
        this.commodityApplyId = commodityApplyId;
        this.storageStockId=storageStockId;
        this.commodityId = commodityId;
        this.commodityClassify = commodityClassify;
        this.commodityName = commodityName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        double price = Arith.mul(quantity,this.unitPrice.doubleValue());
        this.price=new BigDecimal(price);
    }

    public CommodityApplyItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommodityApplyId() {
        return commodityApplyId;
    }

    public void setCommodityApplyId(Integer commodityApplyId) {
        this.commodityApplyId = commodityApplyId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getCommodityClassify() {
        return commodityClassify;
    }

    public void setCommodityClassify(Integer commodityClassify) {
        this.commodityClassify = commodityClassify;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStorageStockId() {
        return storageStockId;
    }

    public void setStorageStockId(Integer storageStockId) {
        this.storageStockId = storageStockId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commodityApplyId=").append(commodityApplyId);
        sb.append(", commodityId=").append(commodityId);
        sb.append(", commodityClassify=").append(commodityClassify);
        sb.append(", commodityName=").append(commodityName);
        sb.append(", quantity=").append(quantity);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append(", price=").append(price);
        sb.append("]");
        return sb.toString();
    }
}