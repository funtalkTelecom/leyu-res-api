package com.leyu.controller;

import com.leyu.config.validator.ValidateGroup;
import com.leyu.config.validator.ValidateUtil;
import com.leyu.dto.Parameter;
import com.leyu.dto.Result;
import com.leyu.pojo.CorpAddress;
import com.leyu.service.CorpAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
	public final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired private CorpAddressService corpAddressService;

	@RequestMapping("/add")
	public Object addressAdd(@RequestBody CorpAddress corpAddress){
        List<Parameter> validateResult = ValidateUtil.validateAnd2Reslut(corpAddress, ValidateGroup.FirstGroup.class);
        if(!validateResult.isEmpty())return new Result(Result.PARAM,validateResult);

        Result result=corpAddressService.add(corpAddress);
		return result;
	}

    @RequestMapping("/list")
    public Result listCommodity() {
        List<?> list= corpAddressService.myCorpAddress();
        return new Result(Result.OK,list);
    }

    @RequestMapping("/del/{id}")
    public Result delCommodity(@PathVariable("id") Integer id) {
        Result result=corpAddressService.del(id);
        return result;
    }

    @RequestMapping("/default/{id}")
    public Result setDefault(@PathVariable("id") Integer id) {
        Result result=corpAddressService.setDefault(id);
        return result;
    }
}
