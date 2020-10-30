package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.pojo.StoragePurchase;
import com.leyu.pojo.StorageStore;
import com.leyu.service.StoragePurchaseService;
import com.leyu.utils.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class StoragePurchaseController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private StoragePurchaseService storagePurchaseService;

	@RequestMapping("/list")
	public Result listStoragePurchase(@RequestBody StoragePurchase storagePurchase) {
		PageInfo<?> pageInfo= storagePurchaseService.storageSerialPages(storagePurchase);
		return new Result(Result.OK,pageInfo);
	}

	@RequestMapping("/add")
	public Result addStoragePurchase(@RequestBody StoragePurchase storagePurchase) {
		storagePurchase.setPurchaseCorpId(SessionUtil.getUserId());//TODO 公司ID
		Result result=storagePurchaseService.reservePurchase(storagePurchase);
		return result;
	}

	@GetMapping("/{storagePurchaseId}")
	public Result getStoragePurchase(@PathVariable("storagePurchaseId") Integer storagePurchaseId) {
		StoragePurchase storagePurchase=storagePurchaseService.get(storagePurchaseId);
		return new Result(Result.OK,storagePurchase);
	}
}
