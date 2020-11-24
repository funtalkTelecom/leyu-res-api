package com.leyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyu.dto.Result;
import com.leyu.mapper.CorporationMapper;
import com.leyu.pojo.Corporation;
import com.leyu.pojo.Permission;
import com.leyu.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

@Service
public class CorpService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CorporationMapper corpMapper;
    @Autowired
    private  ImageService imageService;

    public Result getCorpList(Corporation corp){

        PageHelper.startPage(corp.getStart(),corp.getLimit());
        List<Corporation> list = this.corpMapper.queryList(corp);

        PageInfo<Corporation> pm = new PageInfo<Corporation>(list);
        return new Result(Result.OK, pm);

    }

    public Result save(HttpServletRequest request, MultipartFile licenseImg) throws Exception{

        Result result;
        Corporation corp =new Corporation();

        if (Boolean.valueOf(request.getParameter("imgEditFlag")).booleanValue()){

            result=imageService.saveImage(Constants.UPLOAD_PATH_CORP.getStringKey(),licenseImg);
            if (result.isSuccess())
                corp.setBusinessLicenseImg(result.getData().toString());
            else
            return  result;
        }

        corp.setName(request.getParameter("name"));
        corp.setBusinessLicense(request.getParameter("businessLicense"));
        corp.setContacts(request.getParameter("contacts"));
        corp.setPhone(request.getParameter("phone"));
        corp.setEmail(request.getParameter("email"));
        corp.setAddress(request.getParameter("address"));
        corp.setStatus(Integer.valueOf(request.getParameter("status")));

        String corpId = request.getParameter("id");

        if (corpId != null && !"".equals(corpId)){

            corp.setId(Integer.valueOf(corpId));
            this.corpMapper.updateByPrimaryKeySelective(corp);

        }else {

            this.corpMapper.insertSelective(corp);

        }

        return new Result(Result.OK, "操作成功!");

    }


    public Result deleteCorp(Integer corpId){

        int deleteRet =corpMapper.deleteByPrimaryKey(corpId);

        if (deleteRet >0){
            return new Result(Result.OK, "删除成功!");
        }else{
            return new Result(Result.WARN, "删除失败!");
        }

    }
}
