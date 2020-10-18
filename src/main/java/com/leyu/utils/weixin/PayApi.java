package com.leyu.utils.weixin;

import com.leyu.utils.weixin.kit.HttpKit;
import com.leyu.utils.weixin.kit.Utils;
import java.util.Map;

public class PayApi {
    private static String sendPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public PayApi() {
    }

    public static String sendPay(Map<String, String> params) {
        return HttpKit.post(sendPayUrl, (Map)null, Utils.toXml(params));
    }
}
