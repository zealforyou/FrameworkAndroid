package com.uns.baseapp.mode;

import android.app.Activity;

import com.uns.baseapp.util.CommonUtil;
import com.uns.baseapp.util.DialogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhuo.zhang on 2018/5/29.
 */

public abstract class RxSubs<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        if (!CommonUtil.isNetwork()) {
            onError(AppException.MSG_NO_NETWORK);
            d.dispose();
        }
        if (showDialog() && getContext() != null) {
            DialogUtil.showProgressDialog(getContext());
        }
    }

    @Override
    public final void onNext(T t) {
        DialogUtil.dissmissDialog();
        onSuccess(t);
    }

    @Override
    public final void onError(Throwable e) {

        DialogUtil.dissmissDialog();
        if (e instanceof AppException) {
            AppException appExc = (AppException) e;
            onError(appExc.getMsg());
        } else {
            onError("未知错误");
        }

    }

    @Override
    public final void onComplete() {

    }

    protected boolean showDialog() {
        return true;
    }

    protected abstract Activity getContext();

    public abstract void onSuccess(T entity);

    public abstract void onError(String msg);
}
