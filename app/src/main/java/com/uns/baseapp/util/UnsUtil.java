package com.uns.baseapp.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuo.zhang on 2018/5/29.
 */

public class UnsUtil {
    public static String getUrlParam(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        if (params == null) return "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() == 0) {
                sb.append(entry.getKey() + "=" + entry.getValue());
            } else {
                sb.append("&" + entry.getKey() + "=" + entry.getValue());
            }
        }
        return sb.toString();
    }

    public static Map<String, String> getParamFromUrl(String urlParam) {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(urlParam)) {
            String[] split = urlParam.split("&");
            for (int i = 0; i < split.length; i++) {
                String[] param = split[i].split("=");
                if (param.length > 0)
                    map.put(param[0], param.length > 1 ? param[1] : "");
            }
        }
        return map;
    }

    public static boolean isPrice(String value) {
        if (value == null) return false;
        double num;
        try {
            num = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return num > 0;
    }

    public static boolean isValidMoney(String money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        if (TextUtils.isEmpty(money)||bigDecimal.doubleValue() == 0) return false;
        String regex = "^(([1-9]\\d*)|0)(\\.\\d{0,2})?$";
        return money.matches(regex);
    }
}
