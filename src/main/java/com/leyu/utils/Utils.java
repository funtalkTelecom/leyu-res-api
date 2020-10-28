package com.leyu.utils;

import com.leyu.dto.SmsBase;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Utils {
	private static Logger log = LoggerFactory.getLogger(Utils.class);


	private static MessageDigest md5 = null;
	private static BASE64Encoder base64Encoder = null;
	private static char mapTable[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'S', 'X', 'Y', 'Z'};


	/**
	 * 使用md5对消息进行加密
	 * cqc
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public final static String encodeByBase64MD5(String msg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (md5 == null) {
			md5 = MessageDigest.getInstance("MD5");
		}
		if (base64Encoder == null) {
			base64Encoder = new BASE64Encoder();
		}
		return base64Encoder.encode(md5.digest(ObjectUtils.toString(msg).getBytes("UTF-8")));
	}

	/**
	 * 使用Sha1进行加密
	 * @param str
	 * @return
	 */
	public static String encodeBySha1(String str){
		if(str==null||str.length()==0){
			return null;
		}
		char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j*2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	public final static String doHttpPost(Map<String, String> map, String post_url) throws Exception {
		log.info("请求url:" + post_url);
		log.info("请求参数:" + map);
		HttpURLConnection conn = null;
		String imeis = "";
		Set<String> s = map.keySet();
		Collection<String> c = map.values();
		for (Iterator<String> iterator = c.iterator(), iterator1 = s.iterator(); iterator.hasNext(); ) {
			String value = (String) iterator.next();
			String key = (String) iterator1.next();
			imeis += "&" + key + "=" + value;
		}
		OutputStream out = null;
		InputStream inStream = null;
		BufferedReader rd = null;
		StringBuffer tempStr = new StringBuffer();
		try {
			URL url = new URL(post_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			out = conn.getOutputStream();
			out.write(imeis.getBytes("UTF-8"));
			out.flush();
			out.close();
			inStream = conn.getInputStream();
			rd = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
			String tempLine = rd.readLine();
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempLine = rd.readLine();
			}
		} finally {
			try {
				if (rd != null) {
					rd.close();
					rd = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
				if (inStream != null) {
					inStream.close();
					inStream = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tempStr.toString();
	}

	/**
	 * @param offset 距离当前时间的 天偏移量
	 * @param format 返回字符串格式
	 * @return
	 */
	public static String getDate(int offset, String format) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, c.get(Calendar.DATE) + offset);
		return new SimpleDateFormat(format, Locale.ENGLISH).format(c.getTime());
	}

	public static String getDate(int offset, Date date, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + offset);
		return new SimpleDateFormat(format, Locale.ENGLISH).format(c.getTime());
	}

	/**
	 * @param offset 距离date时间的 分偏移量
	 * @param format 返回字符串格式
	 * @return
	 */
	public static String getDate2(int offset, Date date, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		//c.set(Calendar.DATE, c.get(Calendar.MINUTE) + offset);
		c.add(Calendar.MINUTE,offset);
		return new SimpleDateFormat(format, Locale.ENGLISH).format(c.getTime());
	}

	/**
	 * @param offset 距离date时间的 分偏移量
	 * @return
	 */
	public static Date getDate2(int offset, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		//c.set(Calendar.DATE, c.get(Calendar.MINUTE) + offset);
		c.add(Calendar.MINUTE,offset);
		return date;
	}

	/***
	 * 当前时间再加几天
	 * @param date
	 * @param num
	 * @return
	 */
	public static  Date getAddDate(Date date,int num){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, num);
		Date  currdate =currdate = c.getTime();
		return currdate;
	}



	public static int offsetDay(String date, String format) throws ParseException {
		Calendar c1 = Calendar.getInstance();//当前
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.HOUR, 0);
		c1.set(Calendar.SECOND, 0);
		Date d = stringToDate(date, format);
		int days = (int) ((d.getTime() - c1.getTime().getTime()) / (1000 * 3600 * 24));
		return days + 1;
	}

	/**
	 * @param offset 距离当前时间的 天偏移量
	 * @param format 返回字符串格式
	 * @return
	 */
	public static String getDate(Date date, String format) {
		return new SimpleDateFormat(format, Locale.ENGLISH).format(date);
	}

	/**
	 * 将时间转成字符串
	 *
	 * @return
	 * @throws ParseException
	 */
	public final static Date stringToDate(String dateStr, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}

	/**
	 * 将日期类型的参数转换为字符串类型，格式是：yyyyMMdd 20100115
	 *
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String sdate = "";
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdate = sdf.format(date);
		return sdate;
	}
	/**
	 * yyyyMMddHHmmss 把当前时间格式化为指定的格式
	 *
	 * @param aFormat
	 * @return
	 */
	public static String getCurrentDate(String aFormat) {
		return getCurrentDate(new Date(System.currentTimeMillis()), aFormat);
	}

	/**
	 * yyyyMMddHHmmss 把当前时间格式化为指定的格式
	 *
	 * @param date
	 * @param aFormat
	 * @return
	 */
	public static String getCurrentDate(Date date, String aFormat) {
		return new SimpleDateFormat(aFormat).format(date);
	}

	/*
	  double转int
	 */
	public static int doubleToInt(double data) {
		String dataStr=String.valueOf(data);
		String strInt= dataStr.substring(0, dataStr.indexOf("."));
		String strPoint= dataStr.substring(dataStr.indexOf(".") + 1);
		int pointLen=strPoint.length();
		String str ="";
		str =strInt +strPoint;
		if(pointLen==1)
		{
			str +="0";
		}
		return  Integer.parseInt(str);
	}

	/**
	 * 将value值另起一行追加到fileSavePath 后面
	 *
	 * @param bean
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public static void writeFile(File file, String value) throws IOException {
		boolean bool = true;
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "gb2312"));//"UTF-8"
			if (bool) {
				String lineSeparator = "\n";
				value += (lineSeparator);
			}
			out.write(value);
			out.flush();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		}
	}

	public static List<String> trimToArray(String imei, String spit) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isBlank(imei)) return result;
		String[] t = imei.split(spit);
		for (int i = 0; i < t.length; i++) {
			if (StringUtils.isNotBlank(t[i])) result.add(t[i].trim());
		}
		return result;
	}

	public static int getBetweenMonthByCurrent(int year, int mouth) {//201505
		Calendar c1 = Calendar.getInstance();
		Calendar c0 = Calendar.getInstance();
		c0.set(Calendar.MONTH, mouth - 1);
		c0.set(Calendar.YEAR, year);
		c0.set(Calendar.DATE, 1);
		c1.set(Calendar.DATE, 1);
		int beg_mount = ((c1.get(Calendar.YEAR) - c0.get(Calendar.YEAR)) * 12) + c1.get(Calendar.MONTH) - c0.get(Calendar.MONTH);
		//System.out.println(beg_mount);
		return beg_mount;
	}

	public static void main(String args[]) {
		//int i= compareDate("1995-11-12 15:21", "1999-12-11 09:59");
		//System.out.println("i=="+i);

		//

		System.out.println("i=="+doubleToInt(12));
		System.out.println("i=="+doubleToInt(12.00));
		System.out.println("i=="+doubleToInt(0.01));
		System.out.println("i=="+doubleToInt(12.1));
		System.out.println("i=="+doubleToInt(12.01));
	}

   /*
       "1995-11-12 15:21", "1999-12-11 09:59"  DATE1<DATE2 =1
    */
	public static int compareDate(String DATE1, String DATE2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				//System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 敏感内容处理
	 * @param str	需要处理的字符串
	 * @param start_len		开头保留长度
	 * @param end_len		结尾保留长度
	 * @return
	 */
	public static String sensitive(String str,int start_len,int end_len){
		int strLen=StringUtils.length(str);
		if(strLen>(start_len+end_len)){
			int size=(strLen-start_len-end_len)>4?4:(strLen-start_len-end_len);
			return StringUtils.substring(str,0,start_len)+StringUtils.leftPad("",size,"*")+StringUtils.substring(str,strLen-end_len);//eg.6226****1234；张*春
		}else{
			int size=(strLen-start_len);
			return StringUtils.substring(str,0,start_len)+StringUtils.leftPad("",size,"*");//eg.张*
		}
	}

	/**
	 * 判断非负数的整数或者携带一位或者两位的小数
	 *
	 * @function:
	 * @param obj
	 * @return boolean
	 * @exception
	 * @author:
	 * @since  1.0.0
	 */
	public static boolean judgeTwoDecimal(Object obj){
		boolean flag = false;
		try {
			if(obj != null){
				String source = obj.toString();
				// 判断是否是整数或者是携带一位或者两位的小数
				Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,2})?)$");
				if(pattern.matcher(source).matches()){
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
	 * @param value
	 * @return Sting
	 */
	public static String formatFloatNumber(double value) {
		if(value != 0.00){
			DecimalFormat df = new DecimalFormat("########.00");
			return df.format(value);
		}else{
			return "0.00";
		}

	}
	public static String formatFloatNumber(Double value) {
		if(value != null){
			if(value.doubleValue() != 0.00){
				DecimalFormat df = new DecimalFormat("########.00");
				return df.format(value.doubleValue());
			}else{
				return "0.00";
			}
		}
		return "";
	}

	/**
	 * RMB 分-->元
	 *
	 * @return
	 */
	public static Double centDollar(String amt) {
		return Arith.div(NumberUtils.toDouble(amt, 0d), 100);
	}

	/**
	 * RMB 元-->分
	 *
	 * @return
	 */
	public static Double dollarCent(String amt) {
		return Arith.mul(NumberUtils.toDouble(amt, 0d), 100);
	}

	/**
	 * 数字格式化 	小数点后保留2位(四舍五入)
	 *
	 * @param v        待格式化数字
	 * @param currency 1=货币格式
	 * @return
	 */
	public static String convertFormat(Double v, int currency) {
		DecimalFormat bf = new DecimalFormat("#0.00");
		if (currency == 1) bf = new DecimalFormat("###,##0.00");
		return bf.format(v);
	}

	/**
	 * 数字格式化 	小数点后保留2位(四舍五入)
	 *
	 * @param v        待格式化数字
	 * @return
	 */
	public static Double convertFormat(Double v) {
		return NumberUtils.toDouble(convertFormat(v, 0));
	}

	/**
	 * C + 26个英文 中的1个(随机)+MMddHHmm(时间格式)+ 1位数字(随机)+3位自增数(超过3位归为零)
	 * @return
	 */
	public static String randomNoByDateTime(){
		StringBuffer str=new StringBuffer(15);
		str.append("C");
		str.append(mapTable[(int)(mapTable.length * Math.random())]);
		str.append(Utils.getCurrentDate("yy").substring(1, 2));
		str.append(Utils.getCurrentDate("MMddHHmm"));
		str.append(new Random().nextInt(9));
		str.append(StringUtils.leftPad(String.valueOf(countSeq()),3, "0"));
		return str.toString();
	}

	public static String randomNoByDateTime(int count){
		StringBuffer str=new StringBuffer(count);
		for (int i=0;i<count;i++) {
			str.append(new Random().nextInt(9));
		}
		return str.toString();
	}

	/**
	 * 3位自增数(超过3位归为归零)
	 * @return
	 */
	private static int count = 0;
	private static synchronized Integer countSeq(){
		if(count>990)count=0;
		count++;
		return count;
	}

	/**
	 * list String 转 object
	 * @param serials
	 * @return
	 */
	public static List<Object> string2Object(List<String> serials){
		List<Object> serialsObject=new ArrayList<>();
		for (String serial:serials) {
			serialsObject.add(serial);
		}
		return serialsObject;
	}

    public static Element prase_xml(String message) throws DocumentException {
		Document xml = DocumentHelper.parseText(message);
		return xml.getRootElement();
    }

	public static SmsBase getSmsBase(){
		String smsUrl = SystemParam.get("message_url");
		String wxTempUrl = SystemParam.get("template_url");
		String smsTempUrl = SystemParam.get("sms_temp_url");
		return new SmsBase(smsTempUrl,smsUrl,wxTempUrl);
	}
}