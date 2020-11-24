package com.leyu.service;


import com.leyu.dto.Result;
import com.leyu.utils.Constants;
import com.leyu.utils.SystemParam;
import com.leyu.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.lang.reflect.Field;

@Service
public class ImageService {

	public static Logger log = LoggerFactory.getLogger(ImageService.class);

	public Result saveImage(String businessPath,MultipartFile file) throws Exception{

		Result result;
		long startTime=System.currentTimeMillis();

		String newFilePath=getImageRootPath()+"/"+businessPath;

		File dir = new File(newFilePath);
		if (!dir.exists()) dir.mkdirs();

		if (dir.exists()){

			String newFileName =Utils.randomNoByDateTime(6)+file.getOriginalFilename();//获得图片文件随机文件名
			BufferedInputStream in=new BufferedInputStream(file.getInputStream());
			BufferedOutputStream out=out=new BufferedOutputStream(new FileOutputStream(newFilePath+"/"+newFileName));

			int len=-1;
			byte[] b=new byte[1024];
			while((len=in.read(b))!=-1){
				out.write(b,0,len);
			}
			in.close();
			out.close();

			result= new Result(Result.OK, newFileName);
		}else
			result= new Result(Result.WARN, "目录创建失败!");

		 log.info(String.format("保存图片耗时[%s]ms",(System.currentTimeMillis()-startTime)));
         return  result;

	}


	public String  getImageRootPath(){

		String rootPath="";
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("win"))
			rootPath  =SystemParam.get("upload_root_win");
		else
			rootPath  =SystemParam.get("upload_root_linux");

		if (rootPath.endsWith("/") || rootPath.endsWith("\\")) rootPath=rootPath.substring(0,rootPath.length()-1);

        return  rootPath;

	}


	/**
	 * 计算文字像素长度
	 * @param text
	 * @return
	 */
	private static int getTextLength(String text){
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			int wordLength = String.valueOf(text.charAt(i)).getBytes().length;
			if(wordLength > 1){
				length+=(wordLength-1);
			}
		}
		return length%2==0 ? length/2:length/2+1;
	}
}
