package com.uns.baseapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.uns.baseapp.base.MainApplication;

/**
 * Created by zhuo.zhang on 2018/5/29.
 */

public class CommonUtil {

    /**
     * 是否有网络的判断
     */
    public static boolean isNetwork() {
        Context context= MainApplication.getInstance();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected())

        {
            //改变背景或者 处理网络的全局变量
            return false;
        } else {
            //改变背景或者 处理网络的全局变量
            return true;

        }
    }
}
