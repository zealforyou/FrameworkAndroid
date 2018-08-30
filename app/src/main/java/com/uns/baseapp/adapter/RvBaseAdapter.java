package com.uns.baseapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by zhuo.zhang on 2018/2/8.
 */

public class RvBaseAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public RvBaseAdapter(Context context, List<T> datas) {
        super(context, datas);
    }
}
