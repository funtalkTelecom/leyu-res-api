package com.leyu.controller;

import com.leyu.config.annotation.Powers;
import com.leyu.dto.Result;
import com.leyu.pojo.Corporation;
import com.leyu.service.CorpService;
import com.leyu.service.ImageService;
import com.leyu.utils.Constants;
import com.leyu.utils.PowerConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/corp")
public class CorpController {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private CorpService corpService;
    @Autowired private ImageService imageService;

    @RequestMapping("/list")
    public Result getCorpList(@RequestBody Corporation corp) {

        Result  rightResult= corpService.getCorpList(corp);
        return  rightResult;
    }

    @RequestMapping(value = "/addoredit", method = RequestMethod.POST)
    public Result addOrEditCorp(HttpServletRequest request,MultipartFile licenseImg) throws  Exception{

          Result result =corpService.save(request,licenseImg);
          return  result;


    }

    @RequestMapping("/getimage")
    @Powers({PowerConsts.NOLOGINPOWER})
    public void getImageByName(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String imageName = request.getParameter("imageName");

        File file = new File(imageService.getImageRootPath()+"/"+
                             Constants.UPLOAD_PATH_CORP.getStringKey()+"/"+imageName);

        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();
        OutputStream os = response.getOutputStream();
        response.setContentType("image/*");
        os.write(bytes);

    }

    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable("id") Integer corpId){

        return  corpService.deleteCorp(corpId);

    }

}
