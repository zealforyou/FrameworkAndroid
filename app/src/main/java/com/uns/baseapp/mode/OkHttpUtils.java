package com.uns.baseapp.mode;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ZhangZhuo on 2018/1/23.
 */

public class OkHttpUtils {
    private static final int TIME_OUT_READ = 30000;
    private static final int TIME_OUT_WRITE = 30000;
    private static final int TIME_OUT_CONNECT = 10000;
    private static OkHttpUtils instance;
    private OkHttpClient client;
    private Gson gson = new Gson();

    private OkHttpUtils() {
        client = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT_READ, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT_WRITE, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_OUT_CONNECT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
//                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                        LogUtil.i(message);
//                    }
//                }))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    /*返回字符串*/
    public Observable<String> reqStringGet(String url, Map<String, String> params) {
        return request(url, params, true, null, null);
    }

    public Observable<String> reqStringPost(String url, Map<String, String> params) {
        return request(url, params, false, null, null);
    }

    public Observable<String> reqStringJson(String url, Map<String, String> params) {
        return request(url, params, false, null, null, true);
    }

    /*返回实体*/
    public <T> Observable<T> reqBeanGet(String url, Map<String, String> params, Class<T> bean) {
        return request(url, params, true, bean, null);
    }

    public <T> Observable<T> reqBeanPost(String url, Map<String, String> params, Class<T> bean) {
        return request(url, params, false, bean, null);
    }

    public <T> Observable<T> reqBeanJson(String url, Map<String, String> params, Class<T> bean) {
        return request(url, params, false, bean, null, true);
    }

    /*返回集合*/
    public <T> Observable<T> reqListGet(String url, Map<String, String> params, TypeToken<T> type) {
        return request(url, params, true, null, type);
    }

    public <T> Observable<T> reqListPost(String url, Map<String, String> params, TypeToken<T> type) {
        return request(url, params, false, null, type);
    }

    public <T> Observable<T> reqListJson(String url, Map<String, String> params, TypeToken<T> type) {
        return request(url, params, false, null, type, true);
    }

    protected <T> Observable<T> request(final String url, final Map<String, String> params, final boolean methodGet, final
    Class<T> bean, final TypeToken<T> typeToken) {
        return request(url, params, methodGet, bean, typeToken, false);
    }

    protected <T> Observable<T> request(final String url, final Map<String, String> params, final boolean methodGet, final
    Class<T> bean, final TypeToken<T> typeToken, final boolean json) {
        Observable<T> observable = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                String body;
                T result = null;
                if (json) {
                    body = json(url, params);
                } else if (methodGet) {
                    body = get(url + paramToUrl(params));
                } else {
                    body = post(url, params);
                }
                if (!TextUtils.isEmpty(body)) {
                    body = body.trim();
                }
                if (TextUtils.isEmpty(body)) {
                    e.onError(new AppException(AppException.CODE_SERVER_ERROR, AppException.MSG_SERVER_ERROR));
                    return;
                } else if (bean != null) {
                    if (!body.startsWith("{")) {
                        e.onError(new AppException(AppException.CODE_PARSE_ERROR, AppException.MSG_PARSE_ERROR));
                        return;
                    }
                    try {
                        result = gson.fromJson(body, bean);
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        e.onError(new AppException(AppException.CODE_PARSE_ERROR, AppException.MSG_PARSE_ERROR));
                        return;
                    }
                    e.onNext(result);
                } else if (typeToken != null) {
                    if (!(body.startsWith("{") || body.startsWith("["))) {
                        e.onError(new AppException(AppException.CODE_PARSE_ERROR, AppException.MSG_PARSE_ERROR));
                        return;
                    }
                    try {
                        result = gson.fromJson(body, typeToken.getType());
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        e.onError(new AppException(AppException.CODE_PARSE_ERROR, AppException.MSG_PARSE_ERROR));
                        return;
                    }
                    e.onNext(result);
                } else {
                    result = (T) body;
                    e.onNext(result);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                        if (throwable instanceof SocketTimeoutException)
                            throwable=new AppException(AppException.CODE_CONNECT_ERROR,AppException.MSG_CONNECT_ERROR);
                        return Observable.error(throwable);
                    }
                });
        return observable;
    }

    public String paramToUrl(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() == 0) {
                sb.append("?" + entry.getKey() + "=" + entry.getValue());
            } else {
                sb.append("&" + entry.getKey() + "=" + entry.getValue());
            }
        }
        return sb.toString();
    }

    private String json(String url, Map<String, String> params) throws IOException {
        final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(JSON, new Gson().toJson(params));
        final Request request = new Request.Builder().url(url).post(body).build();
        String result = null;
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                result = response.body().string();
            }
        return result;
    }

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .url(url)
                .build();
        Response execute = client.newCall(request).execute();
        if (execute != null && execute.isSuccessful()) {
            return execute.body().string();
        }
        return null;
    }

    private String post(String url, Map<String, String> params) throws IOException {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            bodyBuilder.add(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
        }
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .post(bodyBuilder.build())
                .url(url)
                .build();
            Response execute = client.newCall(request).execute();
            if (execute != null && execute.isSuccessful()) {
                return execute.body().string();
            }
        return null;
    }
}
