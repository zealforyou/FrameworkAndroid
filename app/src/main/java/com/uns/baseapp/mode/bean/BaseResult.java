package com.uns.baseapp.mode.bean;

import java.io.Serializable;

/**
 * Created by zhuo.zhang on 2018/5/29.
 */

public class BaseResult implements Serializable{
    private String result_code;
    private String result_msg;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }
}
