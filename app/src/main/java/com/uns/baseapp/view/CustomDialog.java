package com.uns.baseapp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.uns.baseapp.R;


/**
 * Create custom Dialog windows for your application
 * Custom dialogs rely on custom layouts wich allow you to
 * create and use your own look & feel.
 * <p>
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * <a href="http://my.oschina.net/arthor" target="_blank" rel="nofollow">@author</a> antoine vianey
 */
public class CustomDialog extends Dialog implements View.OnClickListener{

    private TextView tv_title, tv_message, tv_btn, tv_cancel;
    private View view_devide;


    private String str_title = "", str_message = "";
    private String btn = "", canael = "";
    private DialogInterface.OnClickListener
            cancelonclick,
            btnonclick;

    WindowManager windowManager;

    public CustomDialog(Context context, int theme) {
        super(context, R.style.MyDialog);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, String message) {
        this(context, 0);
        str_message = message;
    }

    public CustomDialog setTitle(String title) {
        str_title = title;
        return this;
    }

    public CustomDialog setMessage(String message) {
        str_message = message;
        return this;
    }
    public CustomDialog setLeftButton(String leftButtonText,
                                      DialogInterface.OnClickListener listener) {
        this.canael = leftButtonText;
        this.cancelonclick = listener;

        return this;
    }

    public CustomDialog setRightButton(String rightButtonText,
                                       DialogInterface.OnClickListener listener) {
        this.btn = rightButtonText;
        this.btnonclick = listener;
        return this;
    }

    public CustomDialog setmessage(String message) {
        str_message = message;
        return this;
    }

    public void showDialog() {
        show();
    }
    public CustomDialog setOnClickListener(String str, OnClickListener listener) {
        btn = str;
        btnonclick = listener;
        return this;
    }

    public CustomDialog setOnClickListener(OnClickListener listener) {
        btnonclick = listener;
        return this;
    }

    public CustomDialog setOnCancelClickListener(OnClickListener listener) {
        cancelonclick = listener;
        return this;
    }

    public CustomDialog setOnCancelClickListener(String str, OnClickListener listener) {
        canael = str;
        cancelonclick = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);
        setCancelable(false);
        DisplayMetrics dm = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;// 屏幕宽度

        tv_title = (TextView) findViewById(R.id.tv_title);

        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_message.setWidth(width * 3 / 4);
        tv_btn = (TextView) findViewById(R.id.tv_btn);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        view_devide = findViewById(R.id.view_devide);
        if (!TextUtils.isEmpty(str_title)) {
            tv_title.setText(str_title);
        }

        if (!TextUtils.isEmpty(str_message)) {
            tv_message.setText(str_message);
        }
        if (!TextUtils.isEmpty(btn)) {
            tv_btn.setText(btn);
        }
        if (btnonclick != null) {
            tv_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    btnonclick.onClick(CustomDialog.this, DialogInterface.BUTTON_NEUTRAL);
                    if (CustomDialog.this.isShowing()) {
                        dismiss();
                    }
                }
            });
        } else {
            tv_btn.setOnClickListener(this);
        }
        if (!TextUtils.isEmpty(canael)) {
            tv_cancel.setText(canael);
        }
        if (cancelonclick != null) {
            tv_cancel.setVisibility(View.VISIBLE);
            view_devide.setVisibility(View.VISIBLE);
            tv_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    cancelonclick.onClick(CustomDialog.this, DialogInterface.BUTTON_POSITIVE);
                    if (CustomDialog.this.isShowing()) {
                        dismiss();
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        if (this.isShowing()) {
            dismiss();
        }
    }

}