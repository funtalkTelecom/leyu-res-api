package com.leyu.service;

import com.leyu.mapper.CityMapper;
import com.leyu.pojo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {
    @Autowired private CityMapper cityMapper;
    private static Map<String,List<City>> citys=new HashMap<>();

    public List<City> loadCitys(Integer pid,Integer level){
        String key=pid+"+"+level;
        if(citys.get(key)!=null)return citys.get(key);
        City city =new City();
        city.setIsDel(0);
        city.setPid(pid);
        List<City> list=this.cityMapper.select(city);
        for (City city1:list) {
            if(city1.getGrade()>=level)continue;
            List<City> children=this.loadCitys(city1.getId(),level);
            city1.setChildren(children);
        }
        citys.put(key,list);
        return list;
    }

}
