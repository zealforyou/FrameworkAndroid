package com.uns.baseapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uns.baseapp.base.BaseRvAdapter;
import com.uns.baseapp.util.UIUtils;

import java.util.List;

/**
 * 友好的适配器-无数据时提示暂无数据
 * Created by zhuo.zhang on 2018/7/19.
 */

public abstract class FriendlyAdapter<T> extends BaseRvAdapter<T> {
    private View noDataView;
    private int height;

    public FriendlyAdapter(Context context, List<T> datas) {
        super(datas);
        noDataView = getViewForNoData(context);
        if (noDataView != null) {
            height = noDataView.getLayoutParams().height;
            addFooter(noDataView);
        }
    }

    @Override
    protected void onSizeChanged(int oldCount, int newCount) {
        super.onSizeChanged(oldCount, newCount);
        if (noDataView == null) return;
        if (newCount > 0) {
            noDataView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = noDataView.getLayoutParams();
            layoutParams.height = 0;
            noDataView.setLayoutParams(layoutParams);

        } else {
            ViewGroup.LayoutParams layoutParams = noDataView.getLayoutParams();
            layoutParams.height = height;
            noDataView.setLayoutParams(layoutParams);
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    protected View getViewForNoData(Context context) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(0, UIUtils.dip2px(10), 0, UIUtils.dip2px(10));
        textView.setTextColor(Color.BLACK);
        textView.setText("暂无数据");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
