package com.leyu.pojo;

import com.leyu.config.validator.ValidateGroup;
import com.leyu.utils.Arith;
import com.leyu.utils.Utils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "tb_commodity_apply")
public class CommodityApply extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer agentCorpId;

    private String agentCorpName;

    @NotNull(message = "请选择运营商",groups ={ValidateGroup.FirstGroup.class})
    private Integer supplyCorpId;

    private String supplyCorpName;

    private Integer payMethodId;

    private String payMethod;

    private BigDecimal subTotal;

    private BigDecimal shippingTotal;

    private BigDecimal commission;

    private BigDecimal adjustPrice;

    private BigDecimal total;

    @Length(max=128,message = "备注不能超过128个字",groups ={ValidateGroup.FirstGroup.class})
    private String addNote;

    private Integer status;

    private Boolean inStorage;

    private Date addDate;

    private Date payDate;

    private Date auditDate;

    private Date pickDate;

    private Date signDate;

    @NotNull(message = "请选择收货地址",groups ={ValidateGroup.FirstGroup.class})
    private Integer addressId;

    private String personName;

    private String personTel;

    private String address;

    private Integer expressId;

    private String expressName;

    private Integer expressNumber;

    private Boolean isDel;

    @Transient
    @Valid
    private List<CommodityApplyItem> commodityApplyItems=new ArrayList<>();

    public CommodityApply(Integer agentCorpId, String agentCorpName, Integer supplyCorpId, String supplyCorpName,Double subTotal, Double shippingTotal,String addNote, Integer status,Integer addressId, String personName, String personTel, String address) {
        this.agentCorpId = agentCorpId;
        this.agentCorpName = agentCorpName;
        this.supplyCorpId = supplyCorpId;
        this.supplyCorpName = supplyCorpName;
        this.subTotal = new BigDecimal(subTotal);
        this.shippingTotal =new BigDecimal(shippingTotal);
        this.commission = new BigDecimal(0d);
        this.adjustPrice = new BigDecimal(0d);
        double price0 = Arith.add(this.subTotal.doubleValue(),this.shippingTotal.doubleValue());
        double price1 = Arith.add(this.commission.doubleValue(),this.adjustPrice.doubleValue());
        double total = Arith.sub(price0,price1);
        this.total = new BigDecimal(total);
        this.addNote = addNote;
        this.status = status;
        this.inStorage = false;
        this.addDate = new Date();
        this.addressId = addressId;
        this.personName = personName;
        this.personTel = personTel;
        this.address = address;
        this.isDel = false;
    }

    public CommodityApply() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentCorpId() {
        return agentCorpId;
    }

    public void setAgentCorpId(Integer agentCorpId) {
        this.agentCorpId = agentCorpId;
    }

    public String getAgentCorpName() {
        return agentCorpName;
    }

    public void setAgentCorpName(String agentCorpName) {
        this.agentCorpName = agentCorpName;
    }

    public Integer getSupplyCorpId() {
        return supplyCorpId;
    }

    public void setSupplyCorpId(Integer supplyCorpId) {
        this.supplyCorpId = supplyCorpId;
    }

    public String getSupplyCorpName() {
        return supplyCorpName;
    }

    public void setSupplyCorpName(String supplyCorpName) {
        this.supplyCorpName = supplyCorpName;
    }

    public Integer getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(Integer payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(BigDecimal adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public String getAddNote() {
        return addNote;
    }

    public void setAddNote(String addNote) {
        this.addNote = addNote;
    }

    public Boolean getInStorage() {
        return inStorage;
    }

    public void setInStorage(Boolean inStorage) {
        this.inStorage = inStorage;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getPickDate() {
        return pickDate;
    }

    public void setPickDate(Date pickDate) {
        this.pickDate = pickDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public Integer getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(Integer expressNumber) {
        this.expressNumber = expressNumber;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public BigDecimal getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(BigDecimal shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public List<CommodityApplyItem> getCommodityApplyItems() {
        return commodityApplyItems;
    }

    public void setCommodityApplyItems(List<CommodityApplyItem> commodityApplyItems) {
        this.commodityApplyItems = commodityApplyItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", agentCorpId=").append(agentCorpId);
        sb.append(", agentCorpName=").append(agentCorpName);
        sb.append(", supplyCorpId=").append(supplyCorpId);
        sb.append(", supplyCorpName=").append(supplyCorpName);
        sb.append(", payMethodId=").append(payMethodId);
        sb.append(", payMethod=").append(payMethod);
        sb.append(", subTotal=").append(subTotal);
        sb.append(", shippingTotal=").append(shippingTotal);
        sb.append(", commission=").append(commission);
        sb.append(", adjustPrice=").append(adjustPrice);
        sb.append(", total=").append(total);
        sb.append(", addNote=").append(addNote);
        sb.append(", status=").append(status);
        sb.append(", inStorage=").append(inStorage);
        sb.append(", addDate=").append(addDate);
        sb.append(", payDate=").append(payDate);
        sb.append(", auditDate=").append(auditDate);
        sb.append(", pickDate=").append(pickDate);
        sb.append(", signDate=").append(signDate);
        sb.append(", addressId=").append(addressId);
        sb.append(", personName=").append(personName);
        sb.append(", personTel=").append(personTel);
        sb.append(", address=").append(address);
        sb.append(", expressId=").append(expressId);
        sb.append(", expressName=").append(expressName);
        sb.append(", expressNumber=").append(expressNumber);
        sb.append(", isDel=").append(isDel);
        sb.append("]");
        return sb.toString();
    }
}