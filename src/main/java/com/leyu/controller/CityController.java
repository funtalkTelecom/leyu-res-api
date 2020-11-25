package com.leyu.controller;

import com.leyu.config.annotation.Powers;
import com.leyu.dto.Result;
import com.leyu.service.CityService;
import com.leyu.utils.PowerConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/city")
public class CityController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private CityService cityService;

	@RequestMapping("/list/{pid}/{level}")
	@Powers({PowerConsts.NOLOGINPOWER})
	public Object listStore(@PathVariable("pid") Integer pid,@PathVariable("level") Integer level) {
		Object object=cityService.loadCitys(pid,level);
		return new Result(Result.OK,object);
	}



}
