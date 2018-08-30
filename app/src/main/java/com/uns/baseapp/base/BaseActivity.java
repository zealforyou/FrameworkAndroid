package com.uns.baseapp.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.uns.baseapp.R;
import com.uns.baseapp.util.SignCheck;

/**
 * Created by ZhangZhuo on 2018/1/23.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textview_title;
    private FrameLayout fl_left;
    private FrameLayout fl_right;
    private View mRlBaseTitle;
    private View mContentView;
    private TextView tv_right;
    private SignCheck mSignCheck;
    private boolean dialogShowing;

    public abstract int layoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogShowing=false;
        mSignCheck = MainApplication.getInstance().getSignCheck();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup baseRoot = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_base, null);
        mRlBaseTitle = baseRoot.findViewById(R.id.rl_base_title);
        textview_title = (TextView) mRlBaseTitle.findViewById(R.id.textview_title);
        tv_right = (TextView) mRlBaseTitle.findViewById(R.id.tv_right);
        fl_left = (FrameLayout) mRlBaseTitle.findViewById(R.id.fl_left);
        fl_right = (FrameLayout) mRlBaseTitle.findViewById(R.id.fl_right);
        fl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (layoutId() != 0) {
            mContentView = getLayoutInflater().inflate(layoutId(), (ViewGroup) baseRoot.findViewById(R.id.my_container));
        }
        init();
        setContentView(baseRoot);
        StatusBarUtil.setColor(this, ActivityCompat.getColor(this, R.color.colorPrimary));
        if (mSignCheck.check()) {
            initView();
            initCtrl();
            initData();
        } else {
            setResult(RESULT_OK, new Intent().putExtra(AppConfig.RESULT_DATA_KEY_MSG, AppConfig.RESULT_MSG_SIGN_IS_ALTERED));
            finish();
        }
    }
    /**
     * 隐藏标题栏
     */
    protected void hideTitle() {
        mRlBaseTitle.setVisibility(View.GONE);
    }

    protected void setTitle(String title) {
        textview_title.setText(title);
    }

    protected void hideButtonLeft() {
        fl_left.setVisibility(View.INVISIBLE);
    }

    protected void setButtonRight(int res, String name, View.OnClickListener listener) {
        fl_right.setVisibility(View.VISIBLE);
        tv_right.setText(name);
        tv_right.setCompoundDrawablesWithIntrinsicBounds(0, res, 0, 0);
        fl_right.setOnClickListener(listener);
    }

    protected Activity getActivity() {
        return this;
    }

    //*******以下方法可以重写，根据自己需要**********
    public void init() {

    }

    public void initView() {

    }

    public void initCtrl() {

    }

    public void initData() {

    }

    //************************************************
    public <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    public void setClick(@IdRes int id, View.OnClickListener click) {
        View viewById = findViewById(id);
        if (viewById != null) {
            viewById.setOnClickListener(click);
        }
    }

    public void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void onClick(View view) {

    }
    protected void finishWithResult(String msg) {
        Intent intent = new Intent();
        intent.putExtra(AppConfig.RESULT_DATA_KEY_MSG, msg);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (dialogShowing) return true;
        return super.dispatchTouchEvent(ev);
    }

    protected void setDialogShowing() {
        dialogShowing = true;
    }

    protected void setDialogMiss() {
        dialogShowing = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
