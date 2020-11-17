package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.pojo.StorageSerial;
import com.leyu.pojo.StorageStock;
import com.leyu.service.StorageSerialService;
import com.leyu.service.StorageStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/serial")
public class StorageSerialController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private StorageSerialService storageSerialService;

	@RequestMapping("/list")
	public Result listStorageStock(@RequestBody StorageSerial storageSerial) {
		PageInfo<?> pageInfo= storageSerialService.myStorageSerialPages(storageSerial);
		return new Result(Result.OK,pageInfo);
	}

}
