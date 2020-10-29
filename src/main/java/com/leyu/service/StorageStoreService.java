package com.leyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.mapper.StorageStoreMapper;
import com.leyu.pojo.StorageStore;
import com.leyu.utils.Constants;
import com.leyu.utils.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StorageStoreService extends BaseService {
	private static Logger log = LogManager.getLogger(StorageStoreService.class);
	@Autowired private StorageStoreMapper storageStoreMapper;


	public List<StorageStore> myStores(){
		return this.findStoresByCorpId(SessionUtil.getUserId());//TODO 公司id
	}

	public PageInfo<?> storePages(StorageStore storageStore){
        PageHelper.startPage(storageStore.getStart(),storageStore.getLimit());
//        List<StorageStore> list = this.storageStoreMapper.queryList(storageStore);
        List<StorageStore> list = this.storageStoreMapper.select(storageStore);
        PageInfo<StorageStore> pm = new PageInfo<StorageStore>(list);
		return pm;
	}

    public StorageStore getStore(Integer storeId){
        return this.storageStoreMapper.selectByPrimaryKey(storeId);
    }

    public Result addStore(String name){
        StorageStore bean=new StorageStore();
        bean.setName(name);
        bean.setCorpId(SessionUtil.getUserId());
        bean.setIsDefault(Constants.APPOINT_VALID.getIntKey());
        bean.setService(Constants.APPOINT_VALID.getIntKey());
        return this.addStore(bean);
    }

	public Result addStore(StorageStore storageStore){
        StorageStore bean=null;
		boolean isAdd=storageStore.getId()==null || storageStore.getId()==0;
        if(isAdd){
            bean=new StorageStore();
        }else{
            bean=this.storageStoreMapper.selectByPrimaryKey(storageStore.getId());
        }

        bean.setName(storageStore.getName());
        bean.setCorpId(storageStore.getCorpId());
        bean.setProvince(storageStore.getProvince());
        bean.setCity(storageStore.getCity());
        bean.setDistrict(storageStore.getDistrict());
        bean.setContacts(storageStore.getContacts());
        bean.setPhone(storageStore.getPhone());
        bean.setAddress(storageStore.getAddress());
        bean.setService(storageStore.getService());
        bean.setIsDefault(storageStore.getIsDefault());
        if(bean.getIsDefault()!=null && bean.getIsDefault()==Constants.APPOINT_VALID.getIntKey()){
            this.clearStoreDefult(bean.getCorpId());
        }

        if(isAdd){
            bean.setAddDate(new Date());
            bean.setDel(false);
            this.storageStoreMapper.insert(bean);
        }else{
            this.storageStoreMapper.updateByPrimaryKey(bean);
        }

		return new Result(Result.OK,"添加成功");
	}

	public Result delStore(Integer storageStoreId){
	    //TODO 检查仓库的库存，若有库存则不允许删除
        StorageStore bean=this.storageStoreMapper.selectByPrimaryKey(storageStoreId);
        bean.setDel(true);
        this.storageStoreMapper.updateByPrimaryKey(bean);
		return new Result(Result.OK,"删除成功");
	}

    public Result clearStoreDefult(Integer corpId){
        List<StorageStore> list=this.findStoresByCorpId(corpId);
        for (StorageStore bean1:list) {
            bean1.setIsDefault(Constants.APPOINT_INVALID.getIntKey());
            this.storageStoreMapper.updateByPrimaryKey(bean1);
        }
        return new Result(Result.OK,"清空成功");
    }

	public Result setStoreDefult(Integer storageStoreId){
        StorageStore bean=this.storageStoreMapper.selectByPrimaryKey(storageStoreId);
        this.clearStoreDefult(bean.getCorpId());
        bean.setIsDefault(Constants.APPOINT_VALID.getIntKey());
        this.storageStoreMapper.updateByPrimaryKey(bean);
		return new Result(Result.OK,"设置成功");
	}

	public List<StorageStore> findStoresByCorpId(Integer corpId){
		StorageStore storageStore=new StorageStore();
		storageStore.setCorpId(corpId);
		storageStore.setDel(false);
		return storageStoreMapper.select(storageStore);
	}

}
