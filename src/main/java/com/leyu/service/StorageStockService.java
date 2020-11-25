package com.leyu.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.config.advice.ServiceException;
import com.leyu.config.utils.RedissonService;
import com.leyu.dto.Result;
import com.leyu.mapper.CommodityMapper;
import com.leyu.mapper.CorporationMapper;
import com.leyu.mapper.StorageStockMapper;
import com.leyu.mapper.StorageStoreMapper;
import com.leyu.pojo.*;
import com.leyu.utils.ApiSessionUtil;
import com.leyu.utils.Constants;
import com.leyu.utils.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StorageStockService extends BaseService {
	private static Logger log = LogManager.getLogger(StorageStockService.class);
	@Autowired private StorageStockMapper storageStockMapper;
	@Autowired private StorageStoreMapper storageStoreMapper;
	@Autowired private CorporationMapper corporationMapper;
	@Autowired private CommodityMapper commodityMapper;
    @Autowired private RedissonService redissonService;
    @Autowired private ApiSessionUtil apiSessionUtil;

    public PageInfo<StorageStock> stockPages(StorageStock storageStock){
        Example example=new Example(StorageStock.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDel",false);
        if(storageStock.getCorpId()!=null){
            criteria.andEqualTo("corpId",storageStock.getCorpId());
        }
        if(StringUtils.isNotEmpty(storageStock.getCorpName())){
            String name="%"+storageStock.getCorpName()+"%";
            criteria.andLike("corpName",name);
        }
        if(StringUtils.isNotEmpty(storageStock.getStore())){
            String name="%"+storageStock.getStore()+"%";
            criteria.andLike("store",name);
        }
        if(StringUtils.isNotEmpty(storageStock.getCommodity())){
            String name="%"+storageStock.getCommodity()+"%";
            criteria.andLike("commodity",name);
        }
        example.setOrderByClause(" id desc");
        PageHelper.startPage(storageStock.getStart(),storageStock.getLimit());
        List<StorageStock> list = this.storageStockMapper.selectByExample(example);
        PageInfo<StorageStock> pm = new PageInfo<>(list);
        return pm;
    }

    public PageInfo<StorageStock> myStockPages(StorageStock storageStock){
        storageStock.setCorpId(apiSessionUtil.getUser().getCorpId());
        return stockPages(storageStock);
    }


    public StorageStock initOrFindStorageStock(Integer commodityId,Integer storeId){//TODO 加锁
        StorageStock bean=this.findStorageStock(apiSessionUtil.getUser().getCorpId(),commodityId,storeId);
        if(bean != null){
            Commodity commodity=this.commodityMapper.selectByPrimaryKey(commodityId);
            bean.setCommodityMold(commodity.getSerial());
            return bean;
        }else{
            this.initStorageStock(apiSessionUtil.getUser().getCorpId(),commodityId,storeId);
            return this.initOrFindStorageStock(commodityId,storeId);
        }
    }

    /**
     *处理可用库存
     * @param bean
     * @param quantity 数量 允许正负数
     * @return
     */
    public Result usableStock(StorageStock bean,int quantity){
        bean.setUsableQuantity(bean.getUsableQuantity()+quantity);
        //TODO 添加库存处理日志
        return new Result(Result.OK,"库存处理成功");
    }
    /**
     *处理冻结库存
     * @param bean
     * @param quantity 数量 允许正负数
     * @return
     */
    public Result freezeStock(StorageStock bean,int quantity){
        bean.setFreezeQuantity(bean.getFreezeQuantity()+quantity);
        //TODO 添加库存处理日志
        return new Result(Result.OK,"库存处理成功");
    }
    /**
     *处理总库存
     * @param bean
     * @param quantity 数量 允许正负数
     * @return
     */
    public Result totalStock(StorageStock bean,int quantity){
        bean.setTotalQuantity(bean.getTotalQuantity()+quantity);
        //TODO 添加库存处理日志
        return new Result(Result.OK,"库存处理成功");
    }

    /**
     * 采购处理 全程事务处理
     *
     * 1、锁定采购单
     * 2、处理采购单状态
     * 3、处理序列表的状态
     * 4、查询并处理库存
     * 5、解锁采购单
     *
     * @return
     */
    public Result purchase(Integer storagePurchase){
        RLock lock = redissonService.getRLock("storage:purchase:"+storagePurchase);
        boolean hasLock = lock.tryLock();
        if(!hasLock)return new Result(Result.ERROR,"当前单据正在处理");
        try {

        }finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 销售单处理 全程事务处理
     *
     * 1、锁定销售单
     * 2、处理采购单状态
     * 3、处理序列表的状态
     * 4、查询并处理库存
     * 5、解锁销售单
     *
     * @return
     */
    public Result sell(Integer storageSell){

        return null;
    }

    /**
     * 销售单处理 全程事务处理
     *
     * 1、锁定销售单
     * 2、处理采购单状态
     * 3、处理序列表的状态
     * 4、查询并处理库存
     * 5、解锁销售单
     *
     * @return
     */
    public Result wsim(Integer wsimOrder){

        return null;
    }

    /**
     * 库存初始化成功
     * @param corpId
     * @param commodityId
     * @param storeId
     * @return
     */
    private Result initStorageStock(Integer corpId,Integer commodityId,Integer storeId){
        StorageStock storageStock=new StorageStock();
        storageStock.setAddDate(new Date());
        storageStock.setIsDel(false);
        storageStock.setCostPrice(BigDecimal.valueOf(0d));
        storageStock.setRetail1Price(BigDecimal.valueOf(0d));
        storageStock.setTotalQuantity(0);
        storageStock.setFreezeQuantity(0);
        storageStock.setUsableQuantity(0);
        storageStock.setCorpId(corpId);
        Corporation corporation = corporationMapper.selectByPrimaryKey(storageStock.getCorpId());
        storageStock.setCorpName(corporation.getShortName());
        storageStock.setCommodityId(commodityId);
        Commodity commodity = commodityMapper.selectByPrimaryKey(storageStock.getCommodityId());
        storageStock.setCommodity(commodity.getName());
        storageStock.setStoreId(storeId);
        StorageStore storageStore=storageStoreMapper.selectByPrimaryKey(storeId);
        storageStock.setStore(storageStore.getName());
        this.storageStockMapper.insert(storageStock);
        log.info("corpId：{} commodityId：{} storeId：{} 库存初始化成功",corpId,commodityId,storeId);
        return new Result(Result.OK,"库存初始化成功");
    }

    /**
     * 查询库存
     * @param corpId
     * @param commodityId
     * @param storeId
     * @return
     */
    public StorageStock findStorageStock(Integer corpId,Integer commodityId,Integer storeId){
        log.debug("查询 corpId：{} commodityId：{} storeId：{} 库存",corpId,commodityId,storeId);
        StorageStock storageStock=new StorageStock();
        storageStock.setStoreId(storeId);
        storageStock.setCorpId(corpId);
        storageStock.setCommodityId(commodityId);
        storageStock.setIsDel(false);
        List<StorageStock> list=this.storageStockMapper.select(storageStock);
        if(list.isEmpty())return null;
        if(list.size()==1)return list.get(0);
        throw new ServiceException("库存单据异常");
    }

    /**
     * 查询库存
     * @param storageStockId
     * @return
     */
    public StorageStock findStorageStock(Integer storageStockId){
        return this.storageStockMapper.selectByPrimaryKey(storageStockId);
    }

    /**
     * 查询某公司对我服务的库存
     */
    public List<StorageStock> findCorpOutStorageStock(Integer corpId){
        Example example=new Example(StorageStock.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDel",false);
        criteria.andEqualTo("corpId",corpId);
        StorageStore storageStore = new StorageStore();
        storageStore.setDel(false);
        storageStore.setCorpId(corpId);
        storageStore.setService(Constants.APPOINT_VALID.getIntKey());
        List<StorageStore> storageStoreList=this.storageStoreMapper.select(storageStore);
        if(storageStoreList.isEmpty())return new ArrayList<StorageStock>();
        List<Object> storageStoreIds=new ArrayList<>();
        for(StorageStore storageStore1:storageStoreList){
            storageStoreIds.add(storageStore1.getId());
        }
        criteria.andIn("storeId",storageStoreIds);
        example.setOrderByClause(" id desc");
        List<StorageStock> list = this.storageStockMapper.selectByExample(example);
        return list;
    }

}
