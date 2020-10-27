package com.leyu.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_storage_purchase")
public class StoragePurchase  extends BasePojo implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer mold;

    private Integer supplyCorpId;

    private String supplyCorp;

    private Integer purchaseCorpId;

    private String purchaseCorp;

    private Integer storeId;

    private Integer status;

    private Integer applicant;

    private Date applyDate;

    private String applyNote;

    private Integer auditor;

    private Date auditDate;

    private String auditNote;

    private Boolean isDel;

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
}