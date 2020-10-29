package com.leyu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
public class StorageStockController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
