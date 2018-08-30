package com.uns.baseapp.mode;

/**
 * Created by zhuo.zhang on 2018/5/29.
 */

public class AppException extends RuntimeException {
    public static final int CODE_SERVER_ERROR = 0x101;
    public static final int CODE_NO_NETWORK = 0x102;
    public static final int CODE_PARSE_ERROR = 0x103;
    public static final int CODE_CONNECT_ERROR = 0x104;

    public static final String MSG_SERVER_ERROR = "服务器出现错误了";
    public static final String MSG_NO_NETWORK = "当前无可用网络，请检查网络后重试";
    public static final String MSG_PARSE_ERROR = "内容解析异常";
    public static final String MSG_CONNECT_ERROR = "网络连接超时";

    private int code;
    private String msg;

    public AppException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
