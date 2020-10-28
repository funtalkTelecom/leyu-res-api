package com.leyu.service;

import com.leyu.mapper.StorageCommodityMapper;
import com.leyu.mapper.StorageStockMapper;
import com.leyu.pojo.StorageCommodity;
import com.leyu.pojo.StorageStock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageCommodityService extends BaseService {
	private static Logger log = LogManager.getLogger(StorageCommodityService.class);
	@Autowired private StorageCommodityMapper storageCommodityMapper;
	@Autowired private StorageStockMapper storageStockMapper;

    /**
     * 查询库存
     * @param source
     * @param sourceId
     * @return
     */
    public List<StorageCommodity> findStorageCommodity(String source, Integer sourceId){
        StorageCommodity storageCommodity=new StorageCommodity();
        storageCommodity.setSource(source);
        storageCommodity.setSourceId(sourceId);
        return this.storageCommodityMapper.select(storageCommodity);
    }

    /**
     * 添加一个商品
     * @param source 详见Constants.COMMODITY_SOURCE_*
     * @param sourceId  单据id
     * @param storageStockId    库存单号
     * @param quantity  数量
     * @return
     */
    public StorageCommodity addStorageCommodity(String source, Integer sourceId,Integer storageStockId,Integer quantity){
        StorageStock storageStock = storageStockMapper.selectByPrimaryKey(storageStockId);
        StorageCommodity storageCommodity=new StorageCommodity(storageStockId,source,sourceId,storageStock.getCommodityId(),storageStock.getCommodity(),quantity);
        int updateCount = this.storageCommodityMapper.insertSelective(storageCommodity);
        log.info("添加一个业务商品单{}，结果：",storageCommodity.getId(),(updateCount==1));
        return storageCommodity;
    }

}
