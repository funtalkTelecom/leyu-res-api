package com.leyu.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.config.advice.ServiceException;
import com.leyu.dto.Result;
import com.leyu.mapper.StorageCommodityMapper;
import com.leyu.mapper.StorageSerialMapper;
import com.leyu.pojo.StorageCommodity;
import com.leyu.pojo.StorageSerial;
import com.leyu.pojo.StorageStock;
import com.leyu.utils.Constants;
import com.leyu.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StorageSerialService extends BaseService {
	private static Logger log = LogManager.getLogger(StorageSerialService.class);
	@Autowired private StorageSerialMapper storageSerialMapper;
	@Autowired private StorageCommodityMapper storageCommodityMapper;
	@Autowired private StorageStockService storageStockService;

    public PageInfo<?> storageSerialPages(StorageSerial storageSerial){
        Example example=new Example(StorageSerial.class);
        Example.Criteria criteria=example.createCriteria();
        example.setOrderByClause(" id desc");
        PageHelper.startPage(storageSerial.getStart(),storageSerial.getLimit());
        List<StorageSerial> list = this.storageSerialMapper.selectByExample(example);
        PageInfo<StorageSerial> pm = new PageInfo<>(list);
        return pm;
    }

    public PageInfo<?> myStorageSerialPages(StorageSerial storageSerial){
        int limit=1000;
        Example example=new Example(StorageSerial.class);
        Example.Criteria criteria=example.createCriteria();
        //1
        if(storageSerial.getStatus()==null){
            Object[] status={Constants.SERIAL_STATUS_2.getIntKey(),Constants.SERIAL_STATUS_2.getIntKey(),
                    Constants.SERIAL_STATUS_3.getIntKey(),Constants.SERIAL_STATUS_4.getIntKey()};
            criteria.andIn("status", Arrays.asList(status));
        }else{
            criteria.andEqualTo("status",storageSerial.getStatus());
        }
        //2
        StorageStock storageStock=new StorageStock();
        storageStock.setCommodity(storageSerial.getCommodity());
        storageStock.setStore(storageSerial.getStore());
        storageStock.setCorpName(storageSerial.getCorpName());
        List<Object> storageStockIds=new ArrayList<>();
        int currPage=1;
        int totalPages=100;
        while (currPage<=totalPages){
            storageStock.setStart(currPage);
            storageStock.setLimit(limit);
            PageInfo<StorageStock> pageInfo = storageStockService.myStockPages(storageStock);
            currPage++;
            totalPages=pageInfo.getPages();
            for(StorageStock storageStock1:pageInfo.getList()){
                storageStockIds.add(storageStock1.getId());
            }
        }
        if(!storageStockIds.isEmpty()){
            criteria.andIn("storageStockId",storageStockIds);
        }
        //3
        if(StringUtils.isNotEmpty(storageSerial.getSerial())){
            Object[] serialArray=StringUtils.split(storageSerial.getSerial(),"\r\n");
            criteria.andIn("serial",Arrays.asList(serialArray));
        }

        example.setOrderByClause(" id desc");
        PageHelper.startPage(storageSerial.getStart(),storageSerial.getLimit());
        List<StorageSerial> list = this.storageSerialMapper.selectByExample(example);

        StorageStock storageStock1=null;
        for(StorageSerial storageSerial1:list){
            if(storageStock1==null || !storageStock1.getId().equals(storageSerial1.getStorageStockId())){
                storageStock1=this.storageStockService.findStorageStock(storageSerial1.getStorageStockId());
            }
            storageSerial1.setCorpName(storageStock1.getCorpName());
            storageSerial1.setCommodity(storageStock1.getCommodity());
            storageSerial1.setStore(storageStock1.getStore());
        }

        PageInfo<StorageSerial> pm = new PageInfo<>(list);
        return pm;
    }

    /**
     * 对某序列号更新为出库
     * @param outStorageCommodityId
     * @return
     */
    public Result updateStorageSerial2Out(Integer outStorageCommodityId){
        StorageCommodity storageCommodity=storageCommodityMapper.selectByPrimaryKey(outStorageCommodityId);
        if(storageCommodity.getCommodityMold()!=Constants.COMMODITY_MOLD_SERIAL.getIntKey())return new Result(Result.OK,"非序列管理，无需更新序列表");

        StorageSerial storageSerial=new StorageSerial();
        storageSerial.setStatus(Constants.SERIAL_STATUS_4.getIntKey());

        Example example=new Example(StorageSerial.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("status",Constants.SERIAL_STATUS_3.getIntKey());
        criteria.andEqualTo("outStorageCommodityId",outStorageCommodityId);

        int quantity = storageCommodity.getQuantity();

        return this.checkUpdateCountAndExpect(storageSerial,example,quantity);
    }

    /**
     * 对某序列号更新为待出库状态
     * @param serials
     * @param outStorageCommodityId
     * @return
     */
    public Result updateStorageSerial2WaitOut(List<String> serials,Integer outStorageCommodityId){
        StorageCommodity storageCommodity=storageCommodityMapper.selectByPrimaryKey(outStorageCommodityId);
        if(storageCommodity.getCommodityMold()!=Constants.COMMODITY_MOLD_SERIAL.getIntKey())return new Result(Result.OK,"非序列管理，无需更新序列表");

        StorageSerial storageSerial=new StorageSerial();
        storageSerial.setStatus(Constants.SERIAL_STATUS_3.getIntKey());
        storageSerial.setOutStorageCommodityId(outStorageCommodityId);

        Example example=new Example(StorageSerial.class);
        Example.Criteria criteria=example.createCriteria();
        List<Object> serialsObject=Utils.string2Object(serials);
        criteria.andEqualTo("status",Constants.SERIAL_STATUS_2.getIntKey());
        criteria.andIn("serial",serialsObject);

        int quantity = storageCommodity.getQuantity();

        return this.checkUpdateCountAndExpect(storageSerial,example,quantity);
    }

    /**
     * 对某个入库商品单进行入库
     * @param inStorageCommodityId
     * @return
     */
    public Result updateStorageSerial2Del(Integer inStorageCommodityId){
        StorageCommodity storageCommodity=storageCommodityMapper.selectByPrimaryKey(inStorageCommodityId);
        if(storageCommodity.getCommodityMold()!=Constants.COMMODITY_MOLD_SERIAL.getIntKey())return new Result(Result.OK,"非序列管理，无需更新序列表");

        StorageSerial storageSerial=new StorageSerial();
        storageSerial.setStatus(Constants.SERIAL_STATUS_0.getIntKey());

        Example example=new Example(StorageSerial.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("inStorageCommodityId",inStorageCommodityId);
        criteria.andEqualTo("status",Constants.SERIAL_STATUS_1.getIntKey());

        int quantity=storageCommodity.getQuantity();
        return this.checkUpdateCountAndExpect(storageSerial,example,quantity);
    }

    /**
     * 对某个入库商品单进行入库
     * @param inStorageCommodityId
     * @return
     */
    public Result updateStorageSerial2In(Integer inStorageCommodityId){
        StorageCommodity storageCommodity=storageCommodityMapper.selectByPrimaryKey(inStorageCommodityId);
        if(storageCommodity.getCommodityMold()!=Constants.COMMODITY_MOLD_SERIAL.getIntKey())return new Result(Result.OK,"非序列管理，无需更新序列表");

        StorageSerial storageSerial=new StorageSerial();
        storageSerial.setStatus(Constants.SERIAL_STATUS_2.getIntKey());

        Example example=new Example(StorageSerial.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("inStorageCommodityId",inStorageCommodityId);
        criteria.andEqualTo("status",Constants.SERIAL_STATUS_1.getIntKey());

        int quantity=storageCommodity.getQuantity();
        return this.checkUpdateCountAndExpect(storageSerial,example,quantity);
    }
    /**
     *
     * @param storageSerial
     * @param example
     * @param expectQuantity
     * @return
     */
    private Result checkUpdateCountAndExpect(StorageSerial storageSerial,Example example,int expectQuantity){
        int updateCount = this.storageSerialMapper.updateByExampleSelective(storageSerial,example);
        log.info("当前预期更新的数量{}，实际更新数量{}",expectQuantity,updateCount);
        if(updateCount!=expectQuantity){
            throw new ServiceException("更新的数量与预期的不一致");
        }
        return new Result(Result.OK,"序列号状态更新成功");
    }


    /**
     * 初始化序列号
     * @param inStorageCommodityId    入库商品单
     * @param mold  模型1iccid;2cardid
     * @param serials   序列集合
     * @return
     */
    public Result addStorageSerial2WaitIn(Integer inStorageCommodityId,Integer mold,List<String> serials){
        Integer inStockCount = this.findStorageSerialsInStockCount(serials);
        if(inStockCount>0)return new Result(Result.OK,"序列号已存在，无法继续添加");

        StorageCommodity storageCommodity=storageCommodityMapper.selectByPrimaryKey(inStorageCommodityId);
        if(storageCommodity.getQuantity()!=serials.size())throw new ServiceException("更新的数量与预期的不一致");
        for (String serial:serials) {
            StorageSerial storageSerial=new StorageSerial(storageCommodity.getStorageStockId(),mold,serial,1,
                    Constants.APPOINT_VALID.getIntKey(),Constants.SERIAL_STATUS_1.getIntKey(),inStorageCommodityId);
            this.storageSerialMapper.insertSelective(storageSerial);
        }
        return new Result(Result.OK,"序列号添加成功");
    }
    /**
     * 查询序列在库数量
     * @param serials
     * @return true存在在库的，false不存在
     */
    public Integer findStorageSerialsInStockCount(List<String> serials){
        Example example=new Example(StorageSerial.class);
        Example.Criteria criteria=example.createCriteria();
        List<Object> statusList=new ArrayList<>();
        statusList.add(Constants.SERIAL_STATUS_1.getIntKey());
        statusList.add(Constants.SERIAL_STATUS_2.getIntKey());
        criteria.andIn("status",statusList);
        List<Object> serialsObject=Utils.string2Object(serials);
        criteria.andIn("serial",serialsObject);
        int inStockCount=this.storageSerialMapper.selectCountByExample(example);
        return inStockCount;
    }
    /**
     * 查询序列信息
     * @param serial
     * @return
     */
    public List<StorageSerial> findStorageSerials(String serial){
        StorageSerial storageStock=new StorageSerial();
        storageStock.setSerial(serial);
        return this.storageSerialMapper.select(storageStock);
    }

    /**
     * 使用入库单商品查询序列号
     * @param inStorageCommodityId
     * @return
     */
    public List<StorageSerial> findByInStorageCommodityId(Integer inStorageCommodityId){
        StorageSerial storageStock=new StorageSerial();
        storageStock.setInStorageCommodityId(inStorageCommodityId);
        return this.storageSerialMapper.select(storageStock);
    }

    /**
     * 使用出库单商品查询序列号
     * @param outStorageCommodityId
     * @return
     */
    public List<StorageSerial> findByOutStorageCommodityId(Integer outStorageCommodityId){
        StorageSerial storageStock=new StorageSerial();
        storageStock.setInStorageCommodityId(outStorageCommodityId);
        return this.storageSerialMapper.select(storageStock);
    }

}
