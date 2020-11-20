package com.leyu.service;

import com.github.abel533.entity.Example;
import com.leyu.dto.Result;
import com.leyu.mapper.CityMapper;
import com.leyu.mapper.CorpAddressMapper;
import com.leyu.pojo.City;
import com.leyu.pojo.Commodity;
import com.leyu.pojo.CorpAddress;
import com.leyu.utils.ApiSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CorpAddressService {
    @Autowired private CorpAddressMapper corpAddressMapper;
    @Autowired private ApiSessionUtil apiSessionUtil;
    @Autowired private CityMapper cityMapper;

    public Result add(CorpAddress corpAddress){
        CorpAddress corpAddress1 = new CorpAddress(
                apiSessionUtil.getUser().getCorpId(),
                corpAddress.getPersonName(),
                corpAddress.getPersonTel(),
                corpAddress.getProvince(),
                corpAddress.getCity(),
                corpAddress.getDistrict(),
                corpAddress.getAddress(),
                apiSessionUtil.getUser().getId()
        );
        this.corpAddressMapper.insert(corpAddress1);
        return new Result(Result.OK,corpAddress1.getId());
    }

    public Result del(Integer corpAddressId){
        CorpAddress corpAddress=this.corpAddressMapper.selectByPrimaryKey(corpAddressId);
        corpAddress.setIsDel(true);
        this.corpAddressMapper.updateByPrimaryKey(corpAddress);
        return new Result(Result.OK,"删除成功");
    }

    public Result setDefault(Integer corpAddressId){
        List<CorpAddress> corpAddressList = myCorpAddress();
        for(CorpAddress corpAddress:corpAddressList){
            if(!corpAddress.getIsDefault())continue;
            corpAddress.setIsDefault(false);
            this.corpAddressMapper.updateByPrimaryKey(corpAddress);
        }

        CorpAddress corpAddress=this.corpAddressMapper.selectByPrimaryKey(corpAddressId);
        corpAddress.setIsDefault(true);
        this.corpAddressMapper.updateByPrimaryKey(corpAddress);
        return new Result(Result.OK,"设置成功");
    }

    public List<CorpAddress> myCorpAddress(){
        Example example=new Example(CorpAddress.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isDel",false);
        criteria.andEqualTo("corpId",apiSessionUtil.getUser().getCorpId());
        example.setOrderByClause(" isDefault desc");
        example.setOrderByClause(" id desc");
        List<CorpAddress> corpAddressList=this.corpAddressMapper.selectByExample(example);
        for (CorpAddress corpAddress:corpAddressList) {
            City city = cityMapper.selectByPrimaryKey(corpAddress.getDistrict());
            if(city==null)continue;
            corpAddress.setFullAddress(city.getFullName()+corpAddress.getAddress());
        }
        return corpAddressList;
    }

    public CorpAddress get(Integer corpAddressId){
        CorpAddress corpAddress=this.corpAddressMapper.selectByPrimaryKey(corpAddressId);
        City district = cityMapper.selectByPrimaryKey(corpAddress.getDistrict());
        if(district != null){
            corpAddress.setFullAddress(district.getFullName()+corpAddress.getAddress());
            corpAddress.setDistrictStr(district.getName());
            City city = cityMapper.selectByPrimaryKey(district.getPid());
            City province = cityMapper.selectByPrimaryKey(city.getPid());
            corpAddress.setCityStr(city.getName());
            corpAddress.setProvinceStr(province.getName());
        }
        return corpAddress;
    }

}
