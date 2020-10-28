package com.leyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.config.advice.ServiceException;
import com.leyu.dto.Result;
import com.leyu.mapper.StoragePurchaseMapper;
import com.leyu.pojo.StorageCommodity;
import com.leyu.pojo.StoragePurchase;
import com.leyu.pojo.StorageStock;
import com.leyu.utils.Constants;
import com.leyu.utils.SessionUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class StoragePurchaseService extends BaseService {
	private static Logger log = LogManager.getLogger(StoragePurchaseService.class);
	@Autowired private StoragePurchaseMapper storagePurchaseMapper;
	@Autowired private StorageCommodityService storageCommodityService;
	@Autowired private StorageSerialService storageSerialService;
	@Autowired private StorageStockService storageStockService;

    public PageInfo<?> storageSerialPages(StoragePurchase storagePurchase){
        PageHelper.startPage(storagePurchase.getStart(),storagePurchase.getLimit());
        List<StoragePurchase> list = this.storagePurchaseMapper.queryList(storagePurchase);
        PageInfo<StoragePurchase> pm = new PageInfo<>(list);
        return pm;
    }

    /**
     * 预入库
     * @return
     */
    @Transactional
    public Result reservePurchase(StoragePurchase storagePurchase){
        Result result=this.reservePurchaseAsPrivate(storagePurchase);
        if(!result.isSuccess())return result;
        return new Result(Result.OK,"单据添加成功");
    }

    /**
     * 预入库
     * @return
     */
    private Result reservePurchaseAsPrivate(StoragePurchase storagePurchase){
        StoragePurchase addPurchase=new StoragePurchase();
        //Integer mold, Integer supplyCorpId, String supplyCorp, Integer purchaseCorpId, String purchaseCorp, Integer storeId, Integer status, Integer applicant, Date applyDate, String applyNote
        storagePurchase.setMold(storagePurchase.getMold());
        storagePurchase.setPurchaseCorpId(storagePurchase.getPurchaseCorpId());
//        storagePurchase.setPurchaseCorp(storagePurchase.getPurchaseCorp());
        storagePurchase.setSupplyCorpId(storagePurchase.getSupplyCorpId());
//        storagePurchase.setSupplyCorp("");
        storagePurchase.setApplicant(SessionUtil.getUserId());
        storagePurchase.setApplyDate(new Date());
        storagePurchase.setApplyNote(storagePurchase.getApplyNote());
        storagePurchase.setStatus(Constants.PURCHASE_STATUS_1.getIntKey());
        log.info("添加采购单");
        this.storagePurchaseMapper.insertSelective(addPurchase);

        for (StorageCommodity storageCommodity:storagePurchase.getStorageCommodityList()) {
            String serials=storageCommodity.getSerials();
            Integer commodityMold=storageCommodity.getCommodityMold();
            log.info("验证{}并添加采购商品[id：{} ；name：{}]，序列号：{}",commodityMold,storageCommodity.getCommodityId(),storageCommodity.getCommodity(),serials);
            if(commodityMold==1 && StringUtils.isEmpty(serials))throw new ServiceException("序列号不存在");

            String[] serialArray=StringUtils.split(serials,"\r\n");
            List<String> serialList=Arrays.asList(serialArray);

            StorageCommodity addstorageCommodity=this.storageCommodityService.addStorageCommodity(Constants.COMMODITY_SOURCE_PURCHASE.getStringKey(),addPurchase.getId(),storageCommodity.getStorageStockId(),storageCommodity.getQuantity());

            Integer serialMold=storageCommodity.getSerialMold();
            if(commodityMold==1){
                log.info("添加单据的序列码");
                this.storageSerialService.addStorageSerial2WaitIn(addstorageCommodity.getId(),serialMold,serialList);
            }
        }
        return new Result(Result.OK,"单据添加成功");
    }

    /**
     * 审核入库
     * @return
     */
    @Transactional
    public Result confirmPurchase(Integer storagePurchaseId,Integer confirm,String remark){
        log.info("审核采购单据{},处理结果：{},处理意见：{}",storagePurchaseId,confirm,remark);
        Integer[] confirms=new Integer[]{1,2};
        if(!ArrayUtils.contains(confirms,confirm))return new Result(Result.ERROR,"未知审核结果");
        StoragePurchase storagePurchase=this.storagePurchaseMapper.selectByPrimaryKey(storagePurchaseId);
        storagePurchase.setAuditor(SessionUtil.getUserId());
        storagePurchase.setAuditDate(new Date());
        storagePurchase.setAuditNote(remark);
        if(confirm==1){//审核通过
            storagePurchase.setStatus(Constants.PURCHASE_STATUS_2.getIntKey());
        }else if(confirm==2){//审核未通过
            storagePurchase.setStatus(Constants.PURCHASE_STATUS_3.getIntKey());
        }
        this.storagePurchaseMapper.updateByPrimaryKeySelective(storagePurchase);

        List<StorageCommodity> storageCommodityList = this.storageCommodityService.findStorageCommodity(Constants.COMMODITY_SOURCE_PURCHASE.getStringKey(),storagePurchaseId);
        for (StorageCommodity storageCommodity:storageCommodityList) {
            if(confirm==1){//审核通过
                StorageStock storageStock = this.storageStockService.findStorageStock(storageCommodity.getStorageStockId());
                log.info("更新采购单据{}的可用库存",storagePurchaseId);
                this.storageStockService.usableStock(storageStock,storageCommodity.getQuantity());
                log.info("更新采购单据{}的总库存",storagePurchaseId);
                this.storageStockService.totalStock(storageStock,storageCommodity.getQuantity());
                log.info("更新采购单据{}的序列的状态至在库",storagePurchaseId);
                this.storageSerialService.updateStorageSerial2In(storageCommodity.getId());
            }else if(confirm==2){//审核未通过
                log.info("更新采购单据{}的序列状态至删除",storagePurchaseId);
                this.storageSerialService.updateStorageSerial2Del(storageCommodity.getId());
            }
        }
        return new Result(Result.OK,"审核处理成功");
    }

    /**
     * 直接入库(不需要经过预入再审核)
     * @return
     */
    @Transactional
    public Result directPurchase(StoragePurchase storagePurchase){
        Result result=this.reservePurchaseAsPrivate(storagePurchase);
        if(!result.isSuccess())return result;
        Integer storagePurchaseId= NumberUtils.toInt(String.valueOf(result.getData()));
        result=this.confirmPurchase(storagePurchaseId,1,"通过");
        if(!result.isSuccess())return result;
        return new Result(Result.OK,"采购单添加成功");
    }
}
