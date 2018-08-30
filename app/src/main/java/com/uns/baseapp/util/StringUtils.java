package com.uns.baseapp.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by zhuo.zhang on 2018/2/8.
 */

public class StringUtils {
    public static final String formatPhone(@NonNull String phone) {
        if (TextUtils.isEmpty(phone)) return "";
        if (phone.length() != 11) return phone;
        StringBuilder sb = new StringBuilder(phone);
        sb.replace(3, 7, "****");
        return sb.toString();
    }

    /**
     * 目前匹配号段
     * 中国电信号段
     * 133、149、153、173、177、180、181、189、199
     * 中国联通号段
     * 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段
     * 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 其他号段
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneLegal(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            String reg = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
            return phone.matches(reg);
        }
        return false;
    }

    /**
     * 格式化卡号 142701xxxxxxxxxxxx格式化成142701 xxxxxxxx xxxx
     *
     * @param s 身份证号码
     */
    public static String formatIdentifyNum(String s) {
        String noblank = s.replaceAll(" ", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < noblank.length(); i++) {
            sb.append(noblank.charAt(i));
            if (i == 5 || i == 13) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * 格式化卡号 142701 *****4553
     *
     * @param s 身份证号码
     */
    public static String formatIdCard(String s) {
        String noblank = s.replaceAll(" ", "");
        String start = noblank.substring(0, 5);
        String end = noblank.substring(noblank.length() - 5, noblank.length() - 1);
        return start + "******" + end;
    }

    /**
     * 格式化卡号 842701 ****4553
     *
     * @param s 银行卡号
     */
    public static String formatBankCard(String s) {
        if (s.length()<7)return s;
        String noblank = s.replaceAll(" ", "");
        String start = noblank.substring(0, noblank.length() - 7);
        String end = noblank.substring(noblank.length() - 3, noblank.length());
        return start + "****" + end;
    }
}
