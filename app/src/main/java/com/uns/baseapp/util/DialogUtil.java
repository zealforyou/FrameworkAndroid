package com.uns.baseapp.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;

import com.uns.baseapp.R;

/**
 * Created by zhuo.zhang on 2018/2/23.
 */

public class DialogUtil {
    private static Dialog dialog;

    public static void showProgressDialog(Activity activity) {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        if (dialog == null) {
            dialog = new Dialog(activity, R.style.MyDialog);
        } else {
            Context context = dialog.getContext();
            if (!(context instanceof Activity)) {
                context = ((ContextWrapper) context).getBaseContext();
            }
            if (context != activity) {
                dialog = new Dialog(activity, R.style.MyDialog);
            }
        }
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void dissmissDialog() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }
}
