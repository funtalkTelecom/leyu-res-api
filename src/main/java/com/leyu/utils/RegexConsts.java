package com.leyu.utils;

public class RegexConsts {
    public static final String REGEX_MOBILE_COMMON = "^((13)|(14)|(15)|(16)|(17)|(18)|(19))\\d{9}$";
    public static final String REGEX_MOBILE_DX = "^((133)|(153)|(17[0,3,7])|(18[0,1,9])|(19[0-9]{1}))\\d{8}$";
    public static final String REGEX_MOBILE_YD = "^((13[4-9])|(147)|(15[0-2,7-9])|(18[2-3,7-8]))\\d{8}$";
    public static final String REGEX_MOBILE_LT = "^((13[0-2])|(145)|(15[5-6])|(18[5-6]))\\d{8}$";
    public static final String REGEX_IC_CARD = "^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\\d{4}(([1][9]\\d{2})|([2]\\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\\d{3}[0-9xX]$";
    public static final String REGEX_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public static final String REGEX_URL= "^((https|http|ftp|rtsp|mms)?://)"
            + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?" //ftp的user@
            + "(([0-9]{1,3}.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
            + "|" // 允许IP和DOMAIN（域名）
            + "([0-9a-z_!~*\'()-]+.)*" // 域名- www.
            + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]." // 二级域名
            + "[a-z]{2,6})" // first level domain- .com or .museum
            + "(:[0-9]{1,4})?" // 端口- :80
            + "((/?)|" // a slash isn't required if there is no file name
            + "(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$";
//    序列号格式：商户号(4位)+业务类型见附录5.4 (4位) +YYYYMMDDHHMISS(14位)+递增序列(6位)
    public static final String REGEX_THIRD_ORDER = "^[a-zA-Z0-9]{4}(0101|0102|0103)\\d{20}$";
    public static final String DATE_YYYYMMDD = "^(\\d{4})(0\\d{1}|1[0-2])(0\\d{1}|[12]\\d{1}|3[01])$";

    public static  boolean matches(String str,String regex){
        if(str==null)return false;
        return str.matches(regex);
    }
}
