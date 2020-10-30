package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.pojo.StorageStore;
import com.leyu.service.StorageStoreService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
public class StorageStoreController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private StorageStoreService storageStoreService;

	@RequestMapping("/list")
	public Result listStore(@RequestBody StorageStore storageStore) {
		PageInfo<?> pageInfo= storageStoreService.storePages(storageStore);
		return new Result(Result.OK,pageInfo);
	}

	@RequestMapping("/add")
	public Result addStore(@RequestBody StorageStore storageStore) {
		if(StringUtils.isEmpty(storageStore.getName()))return new Result(Result.ERROR,"仓库名不能为空");
		Result result=storageStoreService.addStore(storageStore);
		return result;
	}

	@RequestMapping("/{storeId}")
	public Result getStore(@PathVariable("storeId") Integer storeId) {
		StorageStore storageStore=storageStoreService.getStore(storeId);
		return new Result(Result.OK,storageStore);
	}


}
