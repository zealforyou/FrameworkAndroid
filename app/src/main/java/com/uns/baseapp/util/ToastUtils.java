package com.uns.baseapp.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uns.baseapp.R;
import com.uns.baseapp.base.MainApplication;


/**
 * @author peixuan.xie
 *         <p>
 *         吐司
 */
public class ToastUtils {

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
     * @param context
     * @param message
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void showToast(Context context, String message, int duration) {
        long time = System.currentTimeMillis();
        if (toast == null) {
            toast = getToast(context, message);
        }
        toast.setDuration(duration);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView textView = (TextView) linearLayout.getChildAt(0);
        textView.setText(message);
        if (message.equals(oldMsg)) {
            if (time - oneTime > 2000) {
                toast.show();
                oneTime = time;
            }
        } else {
            if (time - oneTime > 2000) {
                toast.show();
                oneTime = time;
            } else {
                toast.cancel();
                toast = getToast(context, message);
                toast.setDuration(duration);
                linearLayout = (LinearLayout) toast.getView();
                textView = (TextView) linearLayout.getChildAt(0);
                textView.setText(message);
                toast.show();
            }
        }
        oldMsg = message;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static Toast getToast(Context context, String message) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        LinearLayout linearLayout = new LinearLayout(context);
        TextView textView = new TextView(context);
        int left = UIUtils.dip2px(10);
        int top = UIUtils.dip2px(16);
        textView.setPadding(left, top, left, top);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthPixels / 2, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setMaxLines(2);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        Drawable drawable = ActivityCompat.getDrawable(context, R.drawable.shape_toast);
        textView.setBackground(drawable);
        toast.setGravity(Gravity.CENTER, 0, 0);
        linearLayout.addView(textView);
        toast.setView(linearLayout);
        return toast;
    }

    public static void showToast(String message) {
        showToast(MainApplication.getInstance(), message, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(String message) {
        showToast(MainApplication.getInstance(), message, Toast.LENGTH_LONG);
    }
}
