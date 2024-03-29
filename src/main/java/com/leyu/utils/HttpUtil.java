package com.leyu.utils;

import com.leyu.dto.Result;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 *
	 * @param url
	 * @param sendR
	 * @param type
	 * @param charset
	 * @param downloadLimit	低于此值则转为字符串，否则转文件
	 * @return
	 * @throws Exception
	 */
	public static Result doHttpPost2Download(String url, String sendR, String type, String charset,int downloadLimit,String root_path,String suffix) throws Exception{
		Result result=doHttpPostForBytes(url,sendR,type,charset);
		if(result.getCode()!=Result.OK)return result;
		byte[] bytes=(byte[]) result.getData();
		if(downloadLimit==-1||bytes.length>downloadLimit){
			String fn=Utils.randomNoByDateTime();
			String file=root_path+fn+"."+suffix;
			FileUtils.copyInputStreamToFile(new ByteArrayInputStream(bytes), new File(file));
			return new Result(Result.OK,file);
		}else{
			return new Result(Result.WARN,input2String((new ByteArrayInputStream(bytes)),charset));
		}
	}

	public static Result doHttpPost(String url, String sendR, String type, String charset) throws Exception{
		Result result=doHttpPostForBytes(url,sendR,type,charset);
		if(!result.isSuccess()) return result;
		byte[] bytes=(byte[]) result.getData();
		return new Result(result.getCode(),input2String((new ByteArrayInputStream(bytes)),charset));
	}

    public static Result doHttpPostForBytes(String url, String sendR, String type, String charset) throws Exception{
        log.info("请求url："+url);
        log.info("请求参数："+sendR);
        log.info("请求contentType："+type);
        log.info("请求字符编码："+charset);
        HttpClient httpClient = new HttpClient();
        HttpMethodBase getMethod = createMethod(url, sendR, type, charset);
        int statusCode = httpClient.executeMethod(getMethod);
        //只要在获取源码中，服务器返回的不是200代码，则统一认为抓取源码失败，返回失败。
        if (statusCode != HttpStatus.SC_OK) {
            log.error("Method failed: " + getMethod.getStatusLine() + "\tstatusCode: " + statusCode);
            return new Result(500, "返回码异常["+statusCode+"]");
        }else{
			byte[] bytes=input2byte(getMethod.getResponseBodyAsStream());
            return new Result(200,bytes);
        }
    }

	private static final byte[] input2byte(InputStream inStream)throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
	private static final String input2String(InputStream inStream,String charet)throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream,charet));
		String readLine = "";
		StringBuffer sb = new StringBuffer();
		while ((readLine = in.readLine()) != null) {
			sb.append(readLine);
		}
		return sb.toString();
	}

    private static HttpMethodBase createMethod(String url, String param, String type, String  charset) throws UnsupportedEncodingException {
		HttpMethodBase httpMethodBase=null;
		if(StringUtils.isEmpty(param)){
			GetMethod method = new GetMethod(url);// 创建GET方法的实例
			httpMethodBase=method;
		}else{
			PostMethod method = new PostMethod(url);
			RequestEntity se = new StringRequestEntity(param, type, charset);
			method.setRequestEntity(se);
			httpMethodBase=method;
		}
        //使用系统提供的默认的恢复策略
		httpMethodBase.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        //设置超时的时间
		httpMethodBase.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 30000);
        return httpMethodBase;
    }
	/**
	 *
	 * HTTP协议POST请求方法
	 */
	public static String sendPost(String url, String params, String charet) throws IOException {
		if (null == charet || "".equals(charet)) {
			charet = "UTF-8";
		}
		StringBuffer sb = new StringBuffer();
		URL urls;
		HttpURLConnection uc = null;
		BufferedReader in = null;
        DataOutputStream out = null;
		try {
			urls = new URL(url);
			uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestMethod("POST");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uc.connect();
			out = new DataOutputStream(uc.getOutputStream());
			out.write(params.getBytes(charet));
			out.flush();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
					charet));
			String readLine = "";
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
		} finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
			if (uc != null) {
				uc.disconnect();
			}
		}
		return sb.toString();
	}

	public static String sendPost(String url, Map<String, ?> paramMap) throws Exception {
		log.info("请求url："+url);
		log.info("请求参数："+paramMap);
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		String param = "";
		Iterator<String> it = paramMap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			param += key + "=" + paramMap.get(key) + "&";
		}

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setConnectTimeout(1000*30);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}
		//使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		log.debug("请求结果："+result);
		return result;
	}


	//发起get请求的方法。
	public static String get(String url,String param){
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			//System.out.println(urlNameString);
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = null;
			if(StringUtils.equals(SystemParam.get("http-proxy-falg"),"true")){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.7.1",58080));
				connection =realUrl.openConnection(proxy);
			}else{
				connection =realUrl.openConnection();
			}
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader( connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}



	/**
	 * 从输入流中获取字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	/**
	 *
	 * @param url
	 * @param params
	 * @param charet
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static void getQrcode(String url, String params, String charet,HttpServletResponse response)throws IOException{
		if (null == charet || "".equals(charet)) {
			charet = "UTF-8";
		}
		URL urls;
		HttpURLConnection uc = null;
		OutputStream os = null;
		DataOutputStream out = null;
		try {
			urls = new URL(url);
			uc = (HttpURLConnection) urls.openConnection();
			if(StringUtils.equals(SystemParam.get("http-proxy-falg"),"true")){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.7.1",58080));
				uc = (HttpsURLConnection) urls.openConnection(proxy);
			}else{
				uc = (HttpsURLConnection) urls.openConnection();
			}
			uc.setRequestMethod("POST");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type", "application/json");
			uc.connect();
			out = new DataOutputStream(uc.getOutputStream());
			out.write(params.getBytes(charet));
			out.flush();
			byte[] buffer = readInputStream(uc.getInputStream());
			os = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("image/jpeg");
			os.write(buffer);
		}finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (uc != null) {
				uc.disconnect();
			}
		}
	}
}