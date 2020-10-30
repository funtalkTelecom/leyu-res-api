package com.leyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.dto.Parameter;
import com.leyu.dto.Result;
import com.leyu.mapper.StorageCommodityMapper;
import com.leyu.mapper.StorageSellMapper;
import com.leyu.mapper.StorageSerialMapper;
import com.leyu.pojo.*;
import com.leyu.utils.Constants;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StorageSellService extends BaseService {
	private static Logger log = LogManager.getLogger(StorageSellService.class);
	@Autowired private StorageSellMapper storageSellMapper;
	@Autowired private StorageCommodityService storageCommodityService;
    @Autowired private StorageStockService storageStockService;
    @Autowired private StorageSerialService storageSerialService;

    public PageInfo<?> storageSerialPages(StorageSell storageSell){
        PageHelper.startPage(storageSell.getStart(),storageSell.getLimit());
        List<StorageSell> list = this.storageSellMapper.queryList(storageSell);
        PageInfo<StorageSell> pm = new PageInfo<>(list);
        return pm;
    }

    /**
     *
     * @return
     */
    @Transactional
    public Result createSell(String source, Integer sourceId,Integer supplyCorpId,Integer purchaseCorpId,Integer storeId,
                             Integer addressId, String personName, String personTel, String address,
                             String unote,List<Parameter> list){
        StorageSell storageSell=new StorageSell();
        storageSell.setMold(Constants.SELL_MOLD_1.getIntKey());
        storageSell.setSource(source);
        storageSell.setSourceId(sourceId);
        storageSell.setSupplyCorpId(supplyCorpId);
//        storageSell.setSupplyCorp();
        storageSell.setPurchaseCorpId(purchaseCorpId);
//        storageSell.setPurchaseCorp();
        storageSell.setStoreId(storeId);
        storageSell.setStatus(Constants.SELL_STATUS_1.getIntKey());
        storageSell.setAddressId(addressId);
        storageSell.setPersonTel(personTel);
        storageSell.setPersonName(personName);
        storageSell.setAddress(address);
        storageSell.setUnote(unote);
        storageSell.setCreateDate(new Date());
        this.storageSellMapper.insertSelective(storageSell);
        for (Parameter parameter:list) {
            int quantity=NumberUtils.toInt(parameter.getKey());
            int commodityId=NumberUtils.toInt(parameter.getValue());
            StorageStock storageStock = storageStockService.findStorageStock(storageSell.getSupplyCorpId(),commodityId,storageSell.getStoreId());
            StorageCommodity storageCommodity=this.storageCommodityService.addStorageCommodity(Constants.COMMODITY_SOURCE_SELL.getStringKey(),storageSell.getId(),storageStock.getId(),quantity);
            log.info("添加销售{}商品单{}",storageSell.getId(),storageCommodity.getId());

            log.info("冻结商品单{}的库存",commodityId);
            this.storageStockService.freezeStock(storageStock,storageCommodity.getQuantity());
        }

        return new Result(Result.OK,"单据添加成功");
    }

    @Transactional
    public Result sellPickup(Integer storageSellId,List<StorageCommodity> storageCommodityList){
        log.info("备货单据:{}",storageSellId);
        StorageSell storageSell=this.storageSellMapper.selectByPrimaryKey(storageSellId);
        storageSell.setStatus(Constants.SELL_STATUS_2.getIntKey());
        storageSell.setPickupDate(new Date());
        this.storageSellMapper.updateByPrimaryKeySelective(storageSell);

        for (StorageCommodity storageCommodity:storageCommodityList) {
            log.info("备货单据:{}",storageSellId);
            List<String> alist=storageCommodity.getSerialList();
            this.storageSerialService.updateStorageSerial2WaitOut(alist,storageCommodity.getId());
        }

        return new Result(Result.OK,"单据添加成功");
    }

    @Transactional
    public Result sellDeliver(Integer storageSellId,Integer expressId,String expressName,String expressNumber,String note){
        log.info("发货单据:{}",storageSellId);
        StorageSell storageSell=this.storageSellMapper.selectByPrimaryKey(storageSellId);
        storageSell.setExpressId(expressId);
        storageSell.setExpressName(expressName);
        storageSell.setExpressNumber(expressNumber);
        storageSell.setNote(note);
        storageSell.setCompleteDate(new Date());
        storageSell.setStatus(Constants.SELL_STATUS_3.getIntKey());

        List<StorageCommodity> storageCommodityList = this.storageCommodityService.findStorageCommodity(Constants.COMMODITY_SOURCE_SELL.getStringKey(),storageSellId);
        for (StorageCommodity storageCommodity:storageCommodityList) {
            StorageStock storageStock = storageStockService.findStorageStock(storageCommodity.getId());
            log.info("释放商品单{}的库存",storageCommodity.getId(),storageCommodity.getQuantity());
            this.storageStockService.freezeStock(storageStock,0-storageCommodity.getQuantity());
            log.info("释放商品单{}的库存",storageCommodity.getId(),storageCommodity.getQuantity());
            this.storageStockService.totalStock(storageStock,0-storageCommodity.getQuantity());

            log.info("出库销售单{}中商品{}的序列单",storageSellId,storageCommodity.getId());
            this.storageSerialService.updateStorageSerial2Out(storageCommodity.getId());
        }

        return new Result(Result.OK,"单据添加成功");
    }
    /**
     * 直接出库
     * @param storageSellId
     * @param expressId
     * @param expressName
     * @param expressNumber
     * @param note
     * @param storageCommodityList
     * @return
     */
    @Transactional
    public Result sellDirectOut(Integer storageSellId,Integer expressId,String expressName,String expressNumber,String note,List<StorageCommodity> storageCommodityList){
        Result result=this.sellPickup(storageSellId,storageCommodityList);
        if(!result.isSuccess())return result;
        result=this.sellDeliver(storageSellId,expressId,expressName,expressNumber,note);
        return result;
    }

    /**
     *
     * @return
     */
    public Result applyCancle(){
        //限未截单的可以申请
        return null;
    }
    /**
     *
     * @return
     */
    public Result affirmCancle(){
        //同意取消后需要关闭单据,释放冻结库存
        return null;
    }
}
