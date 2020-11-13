package com.leyu.service;

import com.leyu.dto.SystemDto;
import com.leyu.mapper.DictMapper;
import com.leyu.pojo.Dict;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictService {
    @Autowired private DictMapper dictMapper;
    private static SystemDto systemDto=null;

    public List<Dict> queryDictByGroup(String group){
        Dict dict =new Dict();
        dict.setGroupKey(group);
        dict.setIsDel(false);
        return this.dictMapper.select(dict);
    }

    public String getSystemKey(String key){
        this.loadSystem();

        for (Dict dict:systemDto.getDictList()) {
            if(StringUtils.equals(dict.getGroupKey(),key))return dict.getText();
        }

        return null;
    }

    public void loadSystem(){
        if(systemDto==null || systemDto.checkExpired()){
            Dict dict=new Dict();
            dict.setPid(1000);//指定pid=1000的为系统配置
            dict.setIsDel(false);
            List<Dict> dictList=this.dictMapper.select(dict);
            systemDto=new SystemDto();
            systemDto.setTimestamp(java.lang.System.currentTimeMillis()+10*60*1000);//10分钟过期
            systemDto.setDictList(dictList);
        }
    }

}
