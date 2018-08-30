package com.uns.baseapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

import com.uns.baseapp.base.MainApplication;

/**
 * Created by 刘志成 on 2017/6/12.
 * email:zhicheng.liu@unspay.com
 * QQ:787799284
 */

public class UIUtils {

    public static int dip2px(double dpValue) {
        float density = MainApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {

        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom != 0;
    }
}
