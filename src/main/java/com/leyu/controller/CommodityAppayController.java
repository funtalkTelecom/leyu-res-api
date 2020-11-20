package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.config.validator.ValidateGroup;
import com.leyu.config.validator.ValidateUtil;
import com.leyu.dto.Parameter;
import com.leyu.dto.Result;
import com.leyu.pojo.CommodityApply;
import com.leyu.service.CommodityApplyService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commodity-apply")
public class CommodityAppayController {
	public final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired private CommodityApplyService commodityApplyService;

	/**
	 * 供应商查询
	 * @param commodityApply
	 * @return
	 */
	@RequestMapping("/supply-list")
	public Result supplyListCommodityApply(@RequestBody CommodityApply commodityApply) {
		PageInfo<?> pageInfo= commodityApplyService.supplyPages(commodityApply);
		return new Result(Result.OK,pageInfo);
	}

	/**
	 * 代理商查询
	 * @param commodityApply
	 * @return
	 */
	@RequestMapping("/list")
	public Result listCommodityApply(@RequestBody CommodityApply commodityApply) {
		PageInfo<?> pageInfo= commodityApplyService.agentPages(commodityApply);
		return new Result(Result.OK,pageInfo);
	}

	@RequestMapping("/add")
	public Result addCommodityApply(@RequestBody CommodityApply commodityApply) {
		List<Parameter> validateResult = ValidateUtil.validateAnd2Reslut(commodityApply, ValidateGroup.FirstGroup.class);
		if(!validateResult.isEmpty())return new Result(Result.PARAM,validateResult);

		Result result=commodityApplyService.add(commodityApply);
		return result;
	}

	@RequestMapping("/{id}")
	public Result getCommodityApply(@PathVariable("id") Integer id) {
		CommodityApply commodity=commodityApplyService.get(id);
		return new Result(Result.OK,commodity);
	}

	@RequestMapping("/audit")
	public Result auditCommodityApply(@RequestBody Map<String, Object> params) {
		Integer id= NumberUtils.toInt(String.valueOf(params.get("id")));
		Double adjustPrice= NumberUtils.toDouble(String.valueOf(params.get("adjustPrice")),0d);
		String result= ObjectUtils.toString(params.get("result"));
		String note= ObjectUtils.toString(params.get("note"));
		Result result1=this.commodityApplyService.audit(id,result,adjustPrice,note);
		return result1;
	}

	@RequestMapping("/pay-result")
	public Result payCommodityApply(@RequestBody Map<String, Object> params) {
		Integer id= NumberUtils.toInt(String.valueOf(params.get("id")));
		Result result=this.commodityApplyService.pay(id);
		return result;
	}

	@RequestMapping("/pick")
	public Result pickCommodityApply(@RequestBody Map<String, Object> params) {
		Integer id= NumberUtils.toInt(String.valueOf(params.get("id")));
		Result result=this.commodityApplyService.send2Pick(id);
		return result;
	}

}
