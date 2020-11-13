package com.leyu.service;

import com.leyu.mapper.CityMapper;
import com.leyu.pojo.City;
import com.leyu.pojo.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired private CityMapper cityMapper;

    public List<City> loadCitys(Integer pid,Integer level){
        City city =new City();
        city.setIsDel(0);
        city.setPid(pid);
        List<City> list=this.cityMapper.select(city);
        for (City city1:list) {
            if(city1.getGrade()>=level)continue;
            List<City> children=this.loadCitys(city1.getId(),level);
            city1.setChildren(children);
        }
        return list;
    }

}
