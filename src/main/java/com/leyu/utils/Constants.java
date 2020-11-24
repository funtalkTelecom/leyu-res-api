package com.leyu.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类为常量类，存放支付的常量,便于统一管理。
 * 命名规则如下
 * 若为系统要用到的常量，则为:参数+有意义的值命名
 * @author 张进春
 *
 */
public enum Constants {

    CORP_LY_ID(10, "乐语公司id", "CORP"),

    /**[有效|无效]/[是|否]*/
    APPOINT_VALID(1, "有效", "APPOINT"),
    APPOINT_INVALID(0, "无效", "APPOINT"),


    SERIAL_STATUS_0(0, "删除", "APPOINT"),
    SERIAL_STATUS_1(1, "预入", "APPOINT"),
    SERIAL_STATUS_2(2, "在库", "APPOINT"),
    SERIAL_STATUS_3(3, "预出", "APPOINT"),
    SERIAL_STATUS_4(4, "已出/用", "APPOINT"),

    COMMODITY_SOURCE_PURCHASE("PURCHASE", "采购", "COMMODITY_SOURCE"),
    COMMODITY_SOURCE_SELL("SELL", "销售", "COMMODITY_SOURCE"),
    COMMODITY_SOURCE_WSIM("WSIM", "写卡", "COMMODITY_SOURCE"),

    PURCHASE_MOLD_1(1, "入库", "SELL_MOLD"),
    PURCHASE_MOLD_2(0, "退库", "SELL_MOLD"),

    PURCHASE_STATUS_1(1, "申请中", "PURCHASE_STATUS"),
    PURCHASE_STATUS_2(2, "已入库", "PURCHASE_STATUS"),
    PURCHASE_STATUS_3(3, "已拒绝", "PURCHASE_STATUS"),

    SELL_MOLD_1(1, "销售", "SELL_MOLD"),
    SELL_MOLD_2(0, "退货", "SELL_MOLD"),

    SELL_STATUS_1(1, "待截单", "SELL_STATUS"),
    SELL_STATUS_2(2, "备货", "SELL_STATUS"),
    SELL_STATUS_3(3, "已出货", "SELL_STATUS"),
    SELL_STATUS_4(4, "待撤销", "SELL_STATUS"),
    SELL_STATUS_5(5, "已撤销", "SELL_STATUS"),


    /**文件上传存储的相对路径*/
    UPLOAD_PATH_CORP("corp", "营业执照", "UPLOAD_PATH"),
    ;

    private Object key;
    private String value;
    private String type;
    private Constants(Object key,String value, String type){
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public Object getKey(){
        return key;
    }
    public int getIntKey() {
        return (Integer) key;
    }
    public String getStringKey() {
        return String.valueOf(key);
    }
    public String getValue() {
        return value;
    }
    public String getType() {
        return type;
    }

    public static Map<Object, Map<Object, String>> map = null;


    public static Map<Object, Map<Object, String>> setValue() {
        Map<Object, Map<Object, String>> _map = new HashMap<Object, Map<Object, String>>();
        Constants[] constants = Constants.values();
        for (Constants constant : constants) {
            Map<Object, String> map = _map.get(constant.getType());
            if(map==null){
                map =new HashMap<Object, String>();
            }
            map.put(constant.getKey(), constant.getValue());
            _map.put(constant.getType(), map);
        }
        return _map;
    }

    public static Map<Object, String> contantsToMap(String type) {
        if(map==null){
            map=setValue();
        }
        return map.get(type);
    }

    public static List<Map<String, Object>> contantsToList(String type){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Constants[] constants = Constants.values();
        for (Constants constant : constants) {
            if(constant.getType().equals(type)){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("key", constant.getKey());
                map.put("value", constant.getValue());
                list.add(map);
            }
        }
        return list;
    }

    public static Object[] getValueObject(String type) {
        Object[] o = null;
        List<Map<String, Object>> list = contantsToList(type);
        if(list !=null && list.size() > 0 ){
            o = new Object[list.size()];
            for (int i = 0; i < list.size(); i++) {
                o[i] = list.get(i).get("value");
            }
        }
        return o;
    }

    public static Object[] getKeyObject(String type) {
        Object[] o = null;
        List<Map<String, Object>> list = contantsToList(type);
        if(list !=null && list.size() > 0 ){
            o = new Object[list.size()];
            for (int i = 0; i < list.size(); i++) {
                o[i] = list.get(i).get("key");
            }
        }
        return o;
    }

}
