package com.uns.baseapp.api;

import com.uns.baseapp.mode.OkHttpUtils;
import com.uns.baseapp.mode.bean.BaseResult;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by zhuo.zhang on 2018/5/28.
 */

public class HttpHelper implements ApiService {
    private OkHttpUtils client;
    private static HttpHelper instance;

    private HttpHelper() {
        client = OkHttpUtils.getInstance();
    }

    public static ApiService getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<BaseResult> sendVerifyCode(Map<String, String> param) {
        return null;
    }
//
//    @Override
//    public Observable<RegisterResult> register(Map<String, String> param) {
//        String url = HttpConfig.BASE_URL + HttpConfig.api.URL_REGISTER;
//        return client.reqBeanPost(url, param, RegisterResult.class);
//    }
}
