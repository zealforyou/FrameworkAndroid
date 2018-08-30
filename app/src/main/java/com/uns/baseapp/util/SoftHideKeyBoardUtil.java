package com.uns.baseapp.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.uns.baseapp.R;

/**
 * Created by zhuo.zhang on 2018/2/8.
 */

public class SoftHideKeyBoardUtil {

    private View contentView;
    private final LinearLayout ll_base_root;
    private int screenHeight;

    public static void assistActivity(Activity activity) {
        new SoftHideKeyBoardUtil(activity);
    }

    private FrameLayout.LayoutParams frameLayoutParams;
    private int contentHeight;//获取setContentView本来view的高度
    private boolean isfirst = true;//只用获取一次

    private SoftHideKeyBoardUtil(Activity activity) {
        ll_base_root = (LinearLayout) activity.findViewById(R.id.ll_base_root);
        if (ll_base_root == null) return;
        contentView = ll_base_root.getChildAt(1);
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        ll_base_root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (isfirst) {
                    contentHeight = ll_base_root.getHeight();//兼容华为等机型
                    isfirst = false;
                }
                possiblyResizeChildOfContent();
            }
        });
    }

    // 获取界面可用高度，如果软键盘弹起后，Activity的xml布局可用高度需要减去键盘高度
    private void possiblyResizeChildOfContent() {
        int usableHeight = computeUsableHeight();
        if (usableHeight == contentHeight) {
            contentView.scrollTo(0, 0);
        } else {
            contentView.scrollTo(0, UIUtils.dip2px(55));
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        ll_base_root.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
}