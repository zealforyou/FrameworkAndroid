package com.uns.baseapp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuo.zhang on 2018/7/19.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseRvAdapter.BaseHolder> {
    private int headerCount;
    private int footerCount;
    private List<View> headers = new ArrayList<>();
    private List<View> footers = new ArrayList<>();
    private List<T> datas;
    private int currentDataCount;

    public BaseRvAdapter(List<T> datas) {
        this.datas = datas;
    }

    public int getHeaderCount() {
        return headerCount;
    }

    public int getFooterCount() {
        return footerCount;
    }

    public View getHeader(int index) {
        if (headers==null||index>=headers.size())return null;
        return headers.get(index);
    }

    @Override
    final public int getItemViewType(int position) {
        int type;
        if (position < headerCount) {//头部视图
            type = position;
        } else if (position > getItemCount() - footerCount - 1) {//底部视图
            type = headerCount + position - (getItemCount() - footerCount);
        } else {//用户视图
            int userViewType = getUserViewType(position);
            type = userViewType + headerCount + footerCount;
        }
        return type;
    }

    public int getUserViewType(int position) {
        return 0;
    }

    @Override
    final public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder baseHolder;
        if (headerCount > 0 && viewType < headerCount) {
            baseHolder = new BaseHolder(headers.get(viewType));
        } else if (footerCount > 0 && viewType < headerCount + footerCount) {
            baseHolder = new BaseHolder(footers.get(viewType - headerCount));
        } else {
            int res = getLayoutId(viewType);
            if (res < 0) {
                throw new IllegalArgumentException("请添加视图");
            }
            LayoutInflater from = LayoutInflater.from(parent.getContext());
            View inflate = from.inflate(res, parent, false);
            baseHolder = new BaseHolder(inflate);
        }
        return baseHolder;
    }

    public abstract int getLayoutId(int viewType);

    public abstract void convert(BaseHolder holder, T item, int position);

    protected void onSizeChanged(int oldCount, int newCount) {

    }

    final public boolean isDataPosition(int itemPosition) {
        return datas.size() != 0 && itemPosition >= headerCount && itemPosition < getItemCount() - footerCount;
    }

    final public int getDataPosition(int itemPosition) {
        if (isDataPosition(itemPosition)) {
            return itemPosition - headerCount;
        }
        return -1;
    }

    final public int getDataCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    final public int getItemCount() {
        int dataCount = datas != null ? datas.size() : 0;
        if (dataCount != currentDataCount) {
            onSizeChanged(currentDataCount, dataCount);
        }
        currentDataCount = dataCount;
        return headerCount + dataCount + footerCount;
    }

    @Override
    final public void onBindViewHolder(BaseHolder holder, int position) {
        if (isDataPosition(position)) {
            T t = datas.get(position - headerCount);
            convert(holder, t, position - headerCount);
        }
    }


    public void addHeader(View header) {
        if (header != null) {
            headers.add(header);
            headerCount = headers.size();
        }
    }

    public void addHeader(int resId, Context context) {
        View inflate = View.inflate(context, resId, null);
        inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        headers.add(inflate);
        headerCount = headers.size();
    }

    public void addFooter(View footer) {
        if (footer != null) {
            footers.add(footer);
            footerCount = footers.size();
        }
    }

    public void removeFooter(View footer) {
        if (footer != null) {
            footers.remove(footer);
            footerCount = footers.size();
        }
    }

    public void addFixedFooter(View footer) {
        if (footer != null) {
            if (footers.size() > 0) {
                footers.add(footers.size() - 1, footer);
            } else {
                footers.add(footer);
            }
            headerCount = headers.size();
        }
    }

    public static class BaseHolder extends RecyclerView.ViewHolder {
        SparseArray<View> views = new SparseArray<View>();

        public BaseHolder(View itemView) {
            super(itemView);
        }

        public View getView(int resId) {
            View view = views.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                views.put(resId, view);
            }
            return view;
        }
    }

}
