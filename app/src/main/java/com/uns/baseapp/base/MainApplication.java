package com.uns.baseapp.base;

import android.app.Application;

import com.uns.baseapp.util.SignCheck;


/**
 * Created by ZhangZhuo on 2018/1/22.
 */

public class MainApplication extends Application {
    private static MainApplication app;
    private SignCheck signCheck;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        signCheck = new SignCheck(app);
    }

    public final static MainApplication getInstance() {
        return app;
    }

    public final SignCheck getSignCheck() {
        return signCheck;
    }

}
