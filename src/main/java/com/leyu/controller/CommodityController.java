package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.config.annotation.Powers;
import com.leyu.config.validator.ValidateGroup;
import com.leyu.config.validator.ValidateUtil;
import com.leyu.dto.Parameter;
import com.leyu.dto.Result;
import com.leyu.pojo.Commodity;
import com.leyu.pojo.StorageStore;
import com.leyu.service.CommodityService;
import com.leyu.service.StorageStoreService;
import com.leyu.utils.PowerConsts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commodity")
public class CommodityController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private CommodityService commodityService;

	@RequestMapping("/list")
	public Result listCommodity(@RequestBody Commodity commodity) {
		PageInfo<?> pageInfo= commodityService.pages(commodity);
		return new Result(Result.OK,pageInfo);
	}

	@RequestMapping("/add")
	public Result addCommodity(@RequestBody Commodity commodity) {
		List<Parameter> validateResult = ValidateUtil.validateAnd2Reslut(commodity, ValidateGroup.FirstGroup.class);
		if(!validateResult.isEmpty())return new Result(Result.PARAM,validateResult);

		Result result=commodityService.addCommodity(commodity);
		return result;
	}

	@RequestMapping("/del/{id}")
	public Result delCommodity(@PathVariable("id") Integer id) {
		Result result=commodityService.delCommodity(id);
		return result;
	}

	@RequestMapping("/{id}")
	public Result getCommodity(@PathVariable("id") Integer id) {
		Commodity commodity=commodityService.get(id);
		return new Result(Result.OK,commodity);
	}


}
