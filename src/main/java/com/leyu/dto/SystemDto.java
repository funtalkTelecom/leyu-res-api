package com.leyu.dto;

import com.leyu.pojo.Dict;

import java.util.List;

public class SystemDto {
	private Long timestamp;
	private List<Dict> dictList;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public List<Dict> getDictList() {
		return dictList;
	}

	public void setDictList(List<Dict> dictList) {
		this.dictList = dictList;
	}

	public boolean checkExpired(){
		if(timestamp==null)return true;
		return (System.currentTimeMillis()-timestamp)>0;
	}
}