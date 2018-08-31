package com.uns.baseapp.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhuo.zhang on 2018/7/12.
 */

public abstract class BaseFragment extends Fragment {

    protected View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(layoutId(), container, false);
        }
        initView();
        initCtrl();
        initData();
        return root;
    }

    protected abstract int layoutId();

    protected void initView() {

    }

    protected void initCtrl() {

    }

    protected void initData() {

    }

    protected <T extends Activity> void startActivity(Class<T> zClass) {
        startActivity(new Intent(getActivity(), zClass));
    }

    protected <T extends View> View findViewById(@IdRes int res) {
        T view = null;
        if (root != null) {
            view = (T) root.findViewById(res);
        }
        return view;
    }

}
