package com.leyu.controller;

import com.github.pagehelper.PageInfo;
import com.leyu.config.validator.ValidateGroup;
import com.leyu.config.validator.ValidateUtil;
import com.leyu.config.validator.ValidatorUtils;
import com.leyu.dto.Parameter;
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

import javax.validation.ConstraintViolation;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/purchase")
public class StoragePurchaseController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private StoragePurchaseService storagePurchaseService;
	@Autowired private ValidatorUtils validatorUtils;

	@RequestMapping("/list")
	public Result listStoragePurchase(@RequestBody StoragePurchase storagePurchase) {
		PageInfo<?> pageInfo= storagePurchaseService.storageSerialPages(storagePurchase);
		return new Result(Result.OK,pageInfo);
	}

	@RequestMapping("/add")
	public Result addStoragePurchase(@RequestBody StoragePurchase storagePurchase) {

		List<Parameter> validateResult = ValidateUtil.validateAnd2Reslut(storagePurchase, ValidateGroup.FirstGroup.class);
		if(!validateResult.isEmpty())return new Result(Result.PARAM,validateResult);

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
