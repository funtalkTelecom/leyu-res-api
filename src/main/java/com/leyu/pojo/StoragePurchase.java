package com.leyu.pojo;

import com.leyu.config.validator.ValidateGroup;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "tb_storage_purchase")
public class StoragePurchase  extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value=1,message = "请选择采购模型",groups ={ValidateGroup.FirstGroup.class})
    private Integer mold;

    @Min(value=1,message = "请选择供应商",groups ={ValidateGroup.FirstGroup.class})
    private Integer supplyCorpId;

    private String supplyCorp;

    private Integer purchaseCorpId;

    private String purchaseCorp;
    @Min(value=1,message = "请选择仓库",groups ={ValidateGroup.FirstGroup.class})
    private Integer storeId;

    private String store;

    private Integer status;

    private Integer applicant;

    @Transient
    private String applicantStr;

    private Date applyDate;
    @Size(max=64,message = "申请备注不能超过64个字",groups ={ValidateGroup.FirstGroup.class})
    private String applyNote;

    private Integer auditor;

    @Transient
    private String auditorStr;

    private Date auditDate;
    @Size(max=64,message = "审核意见不能超过64个字",groups ={ValidateGroup.SecondeGroup.class})
    private String auditNote;

    private Boolean isDel;

    @Transient
    private List<StorageCommodity> storageCommodityList=new ArrayList<>();

    public StoragePurchase(Integer mold, Integer supplyCorpId, String supplyCorp, Integer purchaseCorpId, String purchaseCorp, Integer storeId, Integer status, Integer applicant, Date applyDate, String applyNote) {
        this.mold = mold;
        this.supplyCorpId = supplyCorpId;
        this.supplyCorp = supplyCorp;
        this.purchaseCorpId = purchaseCorpId;
        this.purchaseCorp = purchaseCorp;
        this.storeId = storeId;
        this.status = status;
        this.applicant = applicant;
        this.applyDate = applyDate;
        this.applyNote = applyNote;
        this.isDel = false;
    }

    public StoragePurchase(Integer id, Integer mold, Integer supplyCorpId, String supplyCorp, Integer purchaseCorpId, String purchaseCorp, Integer storeId, Integer status, Integer applicant, Date applyDate, String applyNote, Integer auditor, Date auditDate, String auditNote, Boolean isDel) {
        this.id = id;
        this.mold = mold;
        this.supplyCorpId = supplyCorpId;
        this.supplyCorp = supplyCorp;
        this.purchaseCorpId = purchaseCorpId;
        this.purchaseCorp = purchaseCorp;
        this.storeId = storeId;
        this.status = status;
        this.applicant = applicant;
        this.applyDate = applyDate;
        this.applyNote = applyNote;
        this.auditor = auditor;
        this.auditDate = auditDate;
        this.auditNote = auditNote;
        this.isDel = isDel;
    }

    public StoragePurchase() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyNote() {
        return applyNote;
    }

    public void setApplyNote(String applyNote) {
        this.applyNote = applyNote == null ? null : applyNote.trim();
    }

    public Integer getAuditor() {
        return auditor;
    }

    public void setAuditor(Integer auditor) {
        this.auditor = auditor;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditNote() {
        return auditNote;
    }

    public void setAuditNote(String auditNote) {
        this.auditNote = auditNote == null ? null : auditNote.trim();
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

    public List<StorageCommodity> getStorageCommodityList() {
        return storageCommodityList;
    }

    public void setStorageCommodityList(List<StorageCommodity> storageCommodityList) {
        this.storageCommodityList = storageCommodityList;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getApplicantStr() {
        return applicantStr;
    }

    public void setApplicantStr(String applicantStr) {
        this.applicantStr = applicantStr;
    }

    public String getAuditorStr() {
        return auditorStr;
    }

    public void setAuditorStr(String auditorStr) {
        this.auditorStr = auditorStr;
    }
}