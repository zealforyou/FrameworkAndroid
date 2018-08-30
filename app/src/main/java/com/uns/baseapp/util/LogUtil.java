package com.uns.baseapp.util;

import android.util.Log;

import com.uns.baseapp.BuildConfig;

/**
 * Created by zhuo.zhang on 2018/5/28.
 */

public class LogUtil {
    public static final boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "@uns";

    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }
}
