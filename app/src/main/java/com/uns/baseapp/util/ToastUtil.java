package com.uns.baseapp.util;

import android.widget.Toast;

import com.uns.baseapp.base.MainApplication;

/**
 * Created by zhuo.zhang on 2018/2/8.
 */

public class ToastUtil {

    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast toast = null;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 显示Toast
     *
     * @param message
     */
    public static void showToast(String message) {
        MainApplication context = MainApplication.getInstance();
        long time = System.currentTimeMillis();
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        if (message.equals(oldMsg)) {
            if (time-oneTime> 2000) {
                toast.show();
                oneTime = time;
            }
        }else {
            if (time-oneTime> 2000) {
                toast.show();
                oneTime = time;
            }else {
                toast.cancel();
                toast=Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        oldMsg=message;
    }
}
