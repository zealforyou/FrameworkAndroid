package com.uns.baseapp.base;

import com.uns.baseapp.BuildConfig;

/**
 * Created by zhuo.zhang on 2018/2/7.
 */

public class AppConfig {
    public static final String env = BuildConfig.ENVIROMENT;
    public static final String INENT_KEY_SMS_TEL = "sms_tel";//手机号
    /**
     * 返回结果
     */
    public static final String RESULT_DATA_KEY_MSG = "uns_result";

    //应用被篡改
    public static final String RESULT_MSG_SIGN_IS_ALTERED = "1001|应用被第三方篡改，请联系我们！";
}
