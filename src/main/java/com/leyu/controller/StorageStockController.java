package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.pojo.Commodity;
import com.leyu.pojo.StorageStock;
import com.leyu.service.CommodityService;
import com.leyu.service.StorageStockService;
import com.leyu.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StorageStockController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private StorageStockService storageStockService;
	@Autowired private CommodityService commodityService;


	@RequestMapping("/list")
	public Result listStorageStock(@RequestBody StorageStock storageStock) {
		PageInfo<?> pageInfo= storageStockService.myStockPages(storageStock);
		return new Result(Result.OK,pageInfo);
	}

	@RequestMapping("/store-commodity/{storeId}/{commodityId}")
	public Result addStore(@PathVariable("storeId") Integer storeId,@PathVariable("commodityId") Integer commodityId) {
		if(commodityId==null)return new Result(Result.ERROR,"请选择商品");
		if(storeId==null)return new Result(Result.ERROR,"请选择仓库");
		StorageStock bean=storageStockService.initOrFindStorageStock(commodityId,storeId);
		return new Result(Result.OK,bean);
	}

	@RequestMapping("/corp-tele/{teleId}")
	public Result addStore(@PathVariable("teleId") Integer teleId) {
		if(teleId==null)return new Result(Result.ERROR,"请选择签约的运营商");
		List<Map<String,Object>> list=new ArrayList<>();
		List<StorageStock> storageStockList=storageStockService.findCorpOutStorageStock(teleId);
		for(StorageStock storageStock:storageStockList){
			Commodity commodity=commodityService.get(storageStock.getCommodityId());
			Map<String,Object> map=new HashMap<>();
			map.put("city",commodity.getSectionCity());
			map.put("cityStr",commodity.getSectionCityStr());
			map.put("province",commodity.getSectionProvince());
			map.put("provinceStr",commodity.getSectionProvinceStr());
			map.put("sectionNo",commodity.getSectionNo());
			map.put("classify",commodity.getClassify());
			map.put("quantity",storageStock.getUsableQuantity());
			map.put("price", Utils.convertFormat(storageStock.getRetail1Price().doubleValue(),0));
			map.put("id",storageStock.getId());
			map.put("name",commodity.getName());
			list.add(map);
		}
		return new Result(Result.OK,list);
	}
}
