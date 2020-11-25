package com.leyu.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.config.advice.ServiceException;
import com.leyu.dto.Result;
import com.leyu.mapper.CorporationMapper;
import com.leyu.mapper.StoragePurchaseMapper;
import com.leyu.mapper.StorageStockMapper;
import com.leyu.mapper.StorageStoreMapper;
import com.leyu.pojo.*;
import com.leyu.utils.ApiSessionUtil;
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

import java.util.*;

@Service
public class StoragePurchaseService extends BaseService {
	private static Logger log = LogManager.getLogger(StoragePurchaseService.class);
	@Autowired private StoragePurchaseMapper storagePurchaseMapper;
	@Autowired private StorageCommodityService storageCommodityService;
	@Autowired private StorageSerialService storageSerialService;
	@Autowired private StorageStockService storageStockService;
	@Autowired private StorageStockMapper storageStockMapper;
	@Autowired private StorageStoreMapper storageStoreMapper;
	@Autowired private CorporationMapper corporationMapper;
	@Autowired private ApiSessionUtil apiSessionUtil;

    public PageInfo<?> storageSerialPages(StoragePurchase storagePurchase){
        Example example=new Example(StoragePurchase.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDel",false);
        criteria.andEqualTo("purchaseCorpId",apiSessionUtil.getUser().getCorpId());
        if(StringUtils.isNotEmpty(storagePurchase.getSupplyCorp())){
            String name="%"+storagePurchase.getSupplyCorp()+"%";
            criteria.andLike("supplyCorp",name);
        }
        if(StringUtils.isNotEmpty(storagePurchase.getStore())){
            String name="%"+storagePurchase.getStore()+"%";
            criteria.andLike("store",name);
        }
        example.setOrderByClause(" id desc");
        PageHelper.startPage(storagePurchase.getStart(),storagePurchase.getLimit());
        List<StoragePurchase> list = this.storagePurchaseMapper.selectByExample(example);
        PageInfo<StoragePurchase> pm = new PageInfo<>(list);
        return pm;
    }

    /**
     * 查询采购单的信息
     * @param storagePurchaseId
     * @return
     */
    public StoragePurchase get(Integer storagePurchaseId){
        StoragePurchase storagePurchase = this.storagePurchaseMapper.selectByPrimaryKey(storagePurchaseId);
        List<StorageCommodity> storageCommodities=this.storageCommodityService.findStorageCommodity(Constants.COMMODITY_SOURCE_PURCHASE.getStringKey(),storagePurchaseId);
        for (StorageCommodity storageCommodity:storageCommodities) {
            List<StorageSerial> storageSerials = this.storageSerialService.findByInStorageCommodityId(storageCommodity.getId());
            List<String> serials=new ArrayList<>();
            for (StorageSerial storageSerial:storageSerials) {
                serials.add(storageSerial.getSerial());
            }
            storageCommodity.setSerialList(serials);
        }
        storagePurchase.setStorageCommodityList(storageCommodities);
        return storagePurchase;
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
        //检查添加的串码是否有重复
        Set<String> allserial=new HashSet<>();
        List<String> existList=new ArrayList<>();
        for (StorageCommodity storageCommodity:storagePurchase.getStorageCommodityList()) {
            String serials=storageCommodity.getSerials();
            Integer commodityMold=storageCommodity.getCommodityMold();
            log.info("验证{}并添加采购商品[id：{} ；name：{}]，序列号：{}",commodityMold,storageCommodity.getCommodityId(),storageCommodity.getCommodity(),serials);
            if(commodityMold==1){
                if(StringUtils.isEmpty(serials))return new Result(Result.ERROR,"序列号不存在");
                String[] serialArray=StringUtils.split(serials,"\r\n");
                List<String> serialList=Arrays.asList(serialArray);
                storageCommodity.setSerialList(serialList);
                for (String serial:serialList) {
                    boolean exist=allserial.contains(serial);
                    if(exist){
                        existList.add(serial);
                    }else{
                        allserial.add(serial);
                    }
                }
            }
        }
        if(!existList.isEmpty())return new Result(Result.ERROR,"序列号存在重复");
        //检查添加的串码是否已存在
        String[] allserialArray=allserial.toArray(new String[]{});
        List<String> allserialList=Arrays.asList(allserialArray);
        int existCount=this.storageSerialService.findStorageSerialsInStockCount(allserialList);
        if(existCount>0)return new Result(Result.ERROR,"序列号已在库或即将入库");

        //进行数据添加
        StoragePurchase addPurchase=new StoragePurchase();
        //Integer mold, Integer supplyCorpId, String supplyCorp, Integer purchaseCorpId, String purchaseCorp, Integer storeId, Integer status, Integer applicant, Date applyDate, String applyNote
        addPurchase.setMold(storagePurchase.getMold());
        addPurchase.setPurchaseCorpId(storagePurchase.getPurchaseCorpId());
        Corporation corporation = corporationMapper.selectByPrimaryKey(addPurchase.getPurchaseCorpId());
        addPurchase.setPurchaseCorp(corporation.getName());
        addPurchase.setSupplyCorpId(storagePurchase.getSupplyCorpId());
        corporation = corporationMapper.selectByPrimaryKey(addPurchase.getSupplyCorpId());
        addPurchase.setSupplyCorp(corporation.getName());
        addPurchase.setStoreId(storagePurchase.getStoreId());
        StorageStore storageStore = this.storageStoreMapper.selectByPrimaryKey(addPurchase.getStoreId());
        addPurchase.setStore(storageStore.getName());
        addPurchase.setApplicant(apiSessionUtil.getUser().getId());
        addPurchase.setApplyDate(new Date());
        addPurchase.setApplyNote(storagePurchase.getApplyNote());
        addPurchase.setStatus(Constants.PURCHASE_STATUS_1.getIntKey());
        addPurchase.setIsDel(false);
        log.info("添加采购单");
        this.storagePurchaseMapper.insertSelective(addPurchase);

        for (StorageCommodity storageCommodity:storagePurchase.getStorageCommodityList()) {
            List<String> serialList=storageCommodity.getSerialList();
            Integer commodityMold=storageCommodity.getCommodityMold();
            Integer serialMold=storageCommodity.getSerialMold();
            StorageCommodity addstorageCommodity=this.storageCommodityService.
                    addStorageCommodity(Constants.COMMODITY_SOURCE_PURCHASE.getStringKey(),addPurchase.getId(),storageCommodity.getStorageStockId(),storageCommodity.getQuantity(),commodityMold,serialMold);
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
        if(storagePurchase.getStatus()!=Constants.PURCHASE_STATUS_1.getIntKey())return new Result(Result.ERROR,"当前采购单状态异常");

//        storagePurchase.setAuditor(SessionUtil.getUserId());
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
                this.storageStockMapper.updateByPrimaryKey(storageStock);
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
