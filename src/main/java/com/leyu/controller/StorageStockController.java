package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.pojo.StorageStock;
import com.leyu.service.StorageStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
public class StorageStockController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private StorageStockService storageStockService;


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
}
