package com.leyu.mapper;

import com.github.abel533.mapper.Mapper;
import com.leyu.pojo.City;
import org.springframework.stereotype.Component;

@Component
public interface CityMapper extends Mapper<City>,BaseMapper<City> {
}