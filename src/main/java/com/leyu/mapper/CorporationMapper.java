package com.leyu.mapper;


import com.github.abel533.mapper.Mapper;
import com.leyu.pojo.Corporation;
import org.springframework.stereotype.Component;

@Component
public interface CorporationMapper extends Mapper<Corporation>,BaseMapper<Corporation> {
}