package com.leyu.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "tb_storage_sell")
public class StorageSell  extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //模型1销售;2退货
    private Integer mold;

    private String source;

    private Integer sourceId;

    private Integer supplyCorpId;

    private String supplyCorp;

    private Integer purchaseCorpId;

    private String purchaseCorp;

    private Integer storeId;

    private Integer status;

    private Integer printCount;

    private Integer addressId;

    private String personName;

    private String personTel;

    private String address;

    private Integer expressId;

    private String expressName;

    private String expressNumber;

    private String note;

    private String unote;


    private Date createDate;
    //备货时间
    private Date pickupDate;
    //申请撤销
    private Date applyCancelDate;
    //完成备货或者确认撤销
    private Date completeDate;

    private Boolean isDel;

    @Transient
    private List<StorageCommodity> storageCommodityList=new ArrayList<>();

    public StorageSell(Integer mold, String source, Integer sourceId, Integer supplyCorpId, String supplyCorp, Integer purchaseCorpId, String purchaseCorp, Integer status,Integer addressId, String personName, String personTel, String address,String unote) {
        this.mold = mold;
        this.source = source;
        this.sourceId = sourceId;
        this.supplyCorpId = supplyCorpId;
        this.supplyCorp = supplyCorp;
        this.purchaseCorpId = purchaseCorpId;
        this.purchaseCorp = purchaseCorp;
        this.printCount = 0;
        this.status=status;
        this.addressId = addressId;
        this.personName = personName;
        this.personTel = personTel;
        this.address = address;
        this.unote = unote;
        this.isDel = false;
    }

    public StorageSell(Integer id, Integer mold, String source, Integer sourceId, Integer supplyCorpId, String supplyCorp, Integer purchaseCorpId, String purchaseCorp, Integer status, Integer printCount, Integer addressId, String personName, String personTel, String address, Integer expressId, String expressName, String expressNumber, String note, String unote, Boolean isDel) {
        this.id = id;
        this.mold = mold;
        this.source = source;
        this.sourceId = sourceId;
        this.supplyCorpId = supplyCorpId;
        this.supplyCorp = supplyCorp;
        this.purchaseCorpId = purchaseCorpId;
        this.purchaseCorp = purchaseCorp;
        this.status = status;
        this.printCount = printCount;
        this.addressId = addressId;
        this.personName = personName;
        this.personTel = personTel;
        this.address = address;
        this.expressId = expressId;
        this.expressName = expressName;
        this.expressNumber = expressNumber;
        this.note = note;
        this.unote = unote;
        this.isDel = isDel;
    }

    public StorageSell() {
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

    public Integer getSupplyCorpId() {
        return supplyCorpId;
    }

    public void setSupplyCorpId(Integer supplyCorpId) {
        this.supplyCorpId = supplyCorpId;
    }

    public String getSupplyCorp() {
        return supplyCorp;
    }

    public void setSupplyCorp(String supplyCorp) {
        this.supplyCorp = supplyCorp == null ? null : supplyCorp.trim();
    }

    public Integer getPurchaseCorpId() {
        return purchaseCorpId;
    }

    public void setPurchaseCorpId(Integer purchaseCorpId) {
        this.purchaseCorpId = purchaseCorpId;
    }

    public String getPurchaseCorp() {
        return purchaseCorp;
    }

    public void setPurchaseCorp(String purchaseCorp) {
        this.purchaseCorp = purchaseCorp == null ? null : purchaseCorp.trim();
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
        this.personName = personName == null ? null : personName.trim();
    }

    public String getPersonTel() {
        return personTel;
    }

    public void setPersonTel(String personTel) {
        this.personTel = personTel == null ? null : personTel.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
        this.expressName = expressName == null ? null : expressName.trim();
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber == null ? null : expressNumber.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getUnote() {
        return unote;
    }

    public void setUnote(String unote) {
        this.unote = unote == null ? null : unote.trim();
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Integer getMold() {
        return mold;
    }

    public void setMold(Integer mold) {
        this.mold = mold;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public List<StorageCommodity> getStorageCommodityList() {
        return storageCommodityList;
    }

    public void setStorageCommodityList(List<StorageCommodity> storageCommodityList) {
        this.storageCommodityList = storageCommodityList;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getApplyCancelDate() {
        return applyCancelDate;
    }

    public void setApplyCancelDate(Date applyCancelDate) {
        this.applyCancelDate = applyCancelDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }
}