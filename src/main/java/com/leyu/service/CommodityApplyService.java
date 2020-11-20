package com.leyu.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.dto.Parameter;
import com.leyu.dto.Result;
import com.leyu.mapper.*;
import com.leyu.pojo.*;
import com.leyu.utils.ApiSessionUtil;
import com.leyu.utils.Constants;
import com.leyu.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CommodityApplyService {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired private CommodityApplyMapper commodityApplyMapper;
    @Autowired private CommodityApplyItemMapper commodityApplyItemMapper;
    @Autowired private CommodityMapper commodityMapper;
    @Autowired private StorageStockMapper storageStockMapper;
    @Autowired private ApiSessionUtil apiSessionUtil;
    @Autowired private CorporationMapper corporationMapper;
    @Autowired private CorpAddressService corpAddressService;
    @Autowired private StorageSellService storageSellService;

    public PageInfo<?> agentPages(CommodityApply commodityApply){
        commodityApply.setAgentCorpId(apiSessionUtil.getUser().getCorpId());
        return this.pages(commodityApply);
    }
    public PageInfo<?> supplyPages(CommodityApply commodityApply){
        commodityApply.setSupplyCorpId(apiSessionUtil.getUser().getCorpId());
        return this.pages(commodityApply);
    }
    public PageInfo<?> pages(CommodityApply commodityApply){
        Example example=new Example(CommodityApply.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDel",false);
        if(commodityApply.getAgentCorpId()!=null) {
            criteria.andEqualTo("agentCorpId",commodityApply.getAgentCorpId());
        }
        if(commodityApply.getSupplyCorpId()!=null) {
            criteria.andEqualTo("supplyCorpId",commodityApply.getSupplyCorpId());
        }
        if(commodityApply.getStatus()!=null){
            criteria.andEqualTo("status",commodityApply.getStatus());
        }
        example.setOrderByClause(" id desc");
        PageHelper.startPage(commodityApply.getStart(),commodityApply.getLimit());
        List<CommodityApply> list = this.commodityApplyMapper.selectByExample(example);
        for(CommodityApply bean:list){
            CommodityApplyItem commodityApplyItem=new CommodityApplyItem();
            commodityApplyItem.setCommodityApplyId(bean.getId());
            List<CommodityApplyItem> commodityApplyItemList = this.commodityApplyItemMapper.select(commodityApplyItem);
            bean.setCommodityApplyItems(commodityApplyItemList);
        }
        PageInfo<CommodityApply> pm = new PageInfo<>(list);
        return pm;
    }

    public CommodityApply get(Integer id){
        return commodityApplyMapper.selectByPrimaryKey(id);
    }

    public Result add(CommodityApply commodityApply){
        if(commodityApply.getCommodityApplyItems().isEmpty())return new Result(Result.ERROR,"请添加申请的商品");
        Corporation agentCorp=this.corporationMapper.selectByPrimaryKey(apiSessionUtil.getUser().getCorpId());
        Corporation supplyCorp=this.corporationMapper.selectByPrimaryKey(commodityApply.getSupplyCorpId());
        CorpAddress corpAddress=corpAddressService.get(commodityApply.getAddressId());
        double subTotal=0d;
        double shippingTotal=0d;
        CommodityApply bean=new CommodityApply(
                agentCorp.getId(),
                agentCorp.getName(),
                supplyCorp.getId(),
                supplyCorp.getName(),
                subTotal,
                shippingTotal,
                commodityApply.getAddNote(),
                Constants.COMMODITY_APPLY_STATUS_0.getIntKey(),
                corpAddress.getId(),
                corpAddress.getPersonName(),
                corpAddress.getPersonTel(),
                corpAddress.getFullAddress()
        );
        bean.setId(this.commodityApplyMapper.getId());
        this.commodityApplyMapper.insert(bean);
        for (CommodityApplyItem commodityApplyItem:commodityApply.getCommodityApplyItems()) {
            StorageStock storageStock = storageStockMapper.selectByPrimaryKey(commodityApplyItem.getStorageStockId());
            Commodity commodity = commodityMapper.selectByPrimaryKey(storageStock.getCommodityId());
            CommodityApplyItem itemBean=new CommodityApplyItem(
                    bean.getId(),storageStock.getId(),commodity.getId(),commodity.getClassify(),
                    commodity.getName(),commodityApplyItem.getQuantity(),storageStock.getRetail1Price()
            );
            commodityApplyItemMapper.insert(itemBean);
        }

        bean.setStatus(Constants.COMMODITY_APPLY_STATUS_3.getIntKey());
        this.commodityApplyMapper.updateByPrimaryKey(bean);

        return new Result(Result.OK,"受理成功");
    }

    public Result audit(Integer id,String resulut,Double adjustPrice,String note){
        log.info("单据[{}]正在进行审核[{}],调价[{}]，备注[{}]",id,resulut, Utils.convertFormat(adjustPrice,1),note);
        CommodityApply bean=commodityApplyMapper.selectByPrimaryKey(id);
        if(bean==null)return new Result(Result.ERROR,"单据编号不存在");

        if(bean.getStatus() != Constants.COMMODITY_APPLY_STATUS_3.getIntKey())return new Result(Result.ERROR,"状态异常，无法受理");
        if(StringUtils.equals(resulut,"1")){
            bean.setAdjustPrice(new BigDecimal(adjustPrice));
            bean.setTotal(new BigDecimal(bean.getTotal().doubleValue()-bean.getAdjustPrice().doubleValue()));
            if(bean.getTotal().doubleValue()<=0d){
                return new Result(Result.ERROR,"抱歉调价的金额不能大于应收金额");
            }
            bean.setStatus(Constants.COMMODITY_APPLY_STATUS_1.getIntKey());
        }else{
            bean.setStatus(Constants.COMMODITY_APPLY_STATUS_7.getIntKey());
        }
        bean.setAuditDate(new Date());
        this.commodityApplyMapper.updateByPrimaryKey(bean);

        return new Result(Result.OK,"受理成功");
    }

    public Result pay(Integer id){
        log.info("单据[{}]已支付成功，即将更新为已支付状态",id);
        CommodityApply bean=commodityApplyMapper.selectByPrimaryKey(id);
        if(bean.getStatus() != Constants.COMMODITY_APPLY_STATUS_1.getIntKey())return new Result(Result.ERROR,"状态异常，无法受理");
        bean.setStatus(Constants.COMMODITY_APPLY_STATUS_2.getIntKey());
        //TODO
        bean.setPayMethodId(1);
        bean.setPayMethod("微信支付");
        bean.setPayDate(new Date());
        this.commodityApplyMapper.updateByPrimaryKey(bean);
        return new Result(Result.OK,"受理成功");
    }

    public Result send2Pick(Integer id){

        //todo 锁
        CommodityApply bean=commodityApplyMapper.selectByPrimaryKey(id);
        if(bean.getStatus() != Constants.COMMODITY_APPLY_STATUS_2.getIntKey())return new Result(Result.ERROR,"状态异常，无法受理");
        log.info("单据[{}]即将通知仓库发货，先按仓库分类",bean.getId());
        //按仓库分类，生成不同的出库单
        Map<Integer,List<Parameter>> storeMap=new HashMap<>();
        for(CommodityApplyItem item:bean.getCommodityApplyItems()){
            StorageStock storageStock = this.storageStockMapper.selectByPrimaryKey(item.getStorageStockId());
            Parameter parameter=new Parameter(item.getQuantity()+"",item.getCommodityId()+"");
            if(storeMap.get(storageStock.getStoreId())==null){
                storeMap.put(storageStock.getStoreId(),new ArrayList<>());
            }
            storeMap.get(storageStock.getStoreId()).add(parameter);
        }

        //生成不同的出库单
        Iterator<Integer> iterator = storeMap.keySet().iterator();
        while (iterator.hasNext()){
            int storeId=iterator.next();
            List<Parameter> commodityList=storeMap.get(storeId);
            Result sellResult=storageSellService.createSell(
                    Constants.SELL_SOURCE_ORDER.getStringKey(),
                    bean.getId(),bean.getSupplyCorpId(),bean.getAgentCorpId(),storeId,
                    bean.getAddressId(),bean.getPersonName(),bean.getPersonTel(),bean.getAddress(),bean.getAddNote(),commodityList
            );
            log.info("单据[{}]生成仓库[{}]的出库单，生成结果：",bean.getId(),storeId,sellResult.getData());
        }

        bean.setStatus(Constants.COMMODITY_APPLY_STATUS_4.getIntKey());
        this.commodityApplyMapper.updateByPrimaryKey(bean);
        return new Result(Result.OK,"受理成功");
    }

    public Result send2Deliver(Integer id){
        CommodityApply bean=commodityApplyMapper.selectByPrimaryKey(id);
        if(bean.getStatus() != Constants.COMMODITY_APPLY_STATUS_4.getIntKey())return new Result(Result.ERROR,"状态异常，无法受理");
        bean.setStatus(Constants.COMMODITY_APPLY_STATUS_5.getIntKey());
        bean.setPickDate(new Date());
        this.commodityApplyMapper.updateByPrimaryKey(bean);
        return new Result(Result.OK,"受理成功");
    }

}
