package com.uns.baseapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhuo.zhang on 2018/7/26.
 */

public abstract class LazyFragment extends BaseFragment {
    private boolean isInit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (getUserVisibleHint() && !isInit) {
            isInit = true;
            lazyInitData();
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (root == null) return;
        if (isVisibleToUser && !isInit) {
            isInit = true;
            lazyInitData();
        }
        if (isVisibleToUser) {
            onFragmentShow();
        } else {
            onFragmentHide();
        }
    }

    protected abstract void lazyInitData();

    protected abstract void onFragmentShow();

    protected abstract void onFragmentHide();
}
