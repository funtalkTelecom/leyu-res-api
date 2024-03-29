package com.leyu.utils.weixin.kit;

import java.util.HashMap;
import java.util.Map;

public class ParaMap {
    private Map<String, String> data = new HashMap();

    private ParaMap() {
    }

    public static ParaMap create() {
        return new ParaMap();
    }

    public static ParaMap create(String key, String value) {
        return create().put(key, value);
    }

    public ParaMap put(String key, String value) {
        this.data.put(key, value);
        return this;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
