package com.uns.baseapp.api;

import com.uns.baseapp.mode.bean.BaseResult;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by zhuo.zhang on 2018/5/28.
 */

public interface ApiService {

    Observable<BaseResult> sendVerifyCode(Map<String, String> param);

}
