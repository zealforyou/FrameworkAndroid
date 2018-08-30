package com.uns.baseapp.api;

import com.uns.baseapp.base.AppConfig;

/**
 * Created by ZhangZhuo on 2018/1/23.
 */

public class HttpConfig {
    public static final String RESULT_OK = "0000";
    public static String BASE_URL = "";

    static {
        switch (AppConfig.env) {
            case "release":
                BASE_URL = "http://double.unspay.com/";
                break;
            case "debug":
                BASE_URL = "http://172.22.30.66:8080/";
                break;
            case "ft":
                BASE_URL = "http://172.22.25.61:8086/";
                break;
            case "sit":
                BASE_URL = "http://172.22.22.149:8085/";
                break;
            case "merchant":
                BASE_URL = "http://180.166.114.151:28084/";
                break;
        }
    }

    public static class api {
    }
}
