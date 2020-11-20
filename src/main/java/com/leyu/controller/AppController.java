package com.leyu.controller;

import com.leyu.config.annotation.Powers;
import com.leyu.dto.Result;
import com.leyu.service.DictService;
import com.leyu.utils.PowerConsts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private DictService dictService;

	@RequestMapping("/app-update-check")
	@Powers({PowerConsts.NOLOGINPOWER})
	public Object appUpdateCheck(HttpServletRequest request){
		String brand=request.getParameter("brand");
		String model=request.getParameter("model");
		String system=request.getParameter("system");
		String version=request.getParameter("version");
		String language=request.getParameter("language");
		String platform=request.getParameter("platform");
		String SDKVersion=request.getParameter("SDKVersion");
		String __version=request.getParameter("__version");
		String __environ=request.getParameter("__environ");
		logger.info("brand:{}，model:{}，system:{}，version:{}，language:{}，platform:{}，SDKVersion:{},__environ:{}，__environ：{}",brand,model,system,version,language,platform,SDKVersion,__version,__environ);

		String appVersion=dictService.getSystemKey("app-curr-version");
		String appUrl=dictService.getSystemKey("app-curr-url");
		String appMustUpdate=dictService.getSystemKey("app-curr-must");
		Map<String,Object> map=new HashMap<>();
		map.put("isMustUpdate",Boolean.valueOf(appMustUpdate));
		map.put("url",appUrl);
		map.put("version",appVersion);
		//如何判断版本的大小，若出新版本则更新
		int oldVersion=version2Int(__version);
		int newVersion=version2Int(appVersion);
		map.put("isUpdate",newVersion>oldVersion);
		return new Result(Result.OK,map);
	}

	private int version2Int(String appVersion){
		if(StringUtils.isEmpty(appVersion))return 0;
		String[] versions=appVersion.split("\\.");
		if(versions.length!=3)return 0;
		String v0 =versions[0];
		String v1 = StringUtils.leftPad(versions[1],4,"0");
		String v2 = StringUtils.leftPad(versions[2],4,"0");
		int version= NumberUtils.toInt(v0+v1+v2,1);
		return version;
	}


}
