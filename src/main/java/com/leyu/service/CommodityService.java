package com.leyu.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.dto.SystemDto;
import com.leyu.mapper.CityMapper;
import com.leyu.mapper.CommodityMapper;
import com.leyu.mapper.CorporationMapper;
import com.leyu.mapper.DictMapper;
import com.leyu.pojo.*;
import com.leyu.utils.ApiSessionUtil;
import com.leyu.utils.Constants;
import com.leyu.utils.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommodityService {
    @Autowired private CommodityMapper commodityMapper;
    @Autowired private ApiSessionUtil apiSessionUtil;
    @Autowired private CityMapper cityMapper;
    @Autowired private CorporationMapper corporationMapper;

    public PageInfo<?> pages(Commodity commodity){
        Example example=new Example(Commodity.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("corpId",apiSessionUtil.getUser().getCorpId());
        criteria.andEqualTo("isDel",false);
        criteria.andEqualTo("status",Constants.APPOINT_VALID.getIntKey());
        if(StringUtils.isNotEmpty(commodity.getName())){
            String name="%"+commodity.getName()+"%";
            criteria.andLike("name",name);
        }
        if(commodity.getClassify()!=null){
            criteria.andEqualTo("classify",commodity.getClassify());
        }
        example.setOrderByClause(" id desc");

        PageHelper.startPage(commodity.getStart(),commodity.getLimit());
        List<Commodity> list = this.commodityMapper.selectByExample(example);
        for(Commodity commodity1:list){
            if(commodity1.getSectionCity()==-1)continue;
            City city=cityMapper.selectByPrimaryKey(commodity1.getSectionCity());
            if(city!=null)commodity1.setSectionCityStr(city.getFullName());
            Corporation corporation=corporationMapper.selectByPrimaryKey(commodity1.getCorpId());
            if(corporation!=null)commodity1.setCorpIdStr(corporation.getShortName());
        }
        PageInfo<Commodity> pm = new PageInfo<>(list);
        return pm;
    }

    public Commodity get(Integer id){
        return this.commodityMapper.selectByPrimaryKey(id);
    }

    public Result addCommodity(Commodity commodity){
        Integer corpId=apiSessionUtil.getUser().getCorpId();
        Commodity bean=null;
        boolean isAdd=commodity.getId()==null || commodity.getId()==0;
        if(isAdd){
            bean=new Commodity();
        }else{
            bean=this.commodityMapper.selectByPrimaryKey(commodity.getId());
            if(corpId!=bean.getCorpId()){
                return new Result(Result.OK,"抱歉，您无法修改他人的商品信息");
            }
        }

        bean.setName(commodity.getName());
        bean.setCorpId(corpId);
        bean.setClassify(commodity.getClassify());
        bean.setSectionNo(commodity.getSectionNo());
        bean.setSectionCity(commodity.getSectionCity());
        bean.setSerial(commodity.getSerial());
        bean.setRemark(commodity.getRemark());

        if(isAdd){
            bean.setStatus(commodity.getStatus());
            bean.setAddDate(new Date());
            bean.setAddUser(apiSessionUtil.getUser().getId());
            bean.setDel(false);
            this.commodityMapper.insert(bean);
        }else{
            this.commodityMapper.updateByPrimaryKey(bean);
        }
        return new Result(Result.OK,"受理成功");
    }

    public Result delCommodity(Integer id){
        //TODO 检查仓库的库存，若有库存则不允许删除
        Commodity bean=this.commodityMapper.selectByPrimaryKey(id);
        bean.setDel(true);
        this.commodityMapper.updateByPrimaryKey(bean);
        return new Result(Result.OK,"删除成功");
    }


}
