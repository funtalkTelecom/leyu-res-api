package com.leyu.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_storage_sell")
public class StorageSell  extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer mold;

    private String source;

    private Integer sourceId;

    private Integer supplyCorpId;

    private String supplyCorp;

    private Integer purchaseCorpId;

    private String purchaseCorp;

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

    private Boolean isDel;

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
}