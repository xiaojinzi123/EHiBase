package com.xiaojinzi.ehibase.base.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.xiaojinzi.ehibase.base.view.inter.IBaseView;


/**
 * time   : 2018/03/14
 * blog   : http://blog.csdn.net/u011692041
 *
 * @author : xiaojinzi 30212
 */
public class BaseViewImpl implements IBaseView {

    @Nullable
    private Context mContext;

    private Dialog dialog;

    public BaseViewImpl(@Nullable Context context) {
        mContext = context;
    }

    @Override
    public void showProgress() {
        showProgress(false);
    }

    @Override
    public void showProgress(boolean cancelable) {

        if (null == dialog) {
            ProgressDialog mDialog = new ProgressDialog(mContext);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage("请骚等");    //设置内容
            mDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
            mDialog.show();
            dialog = mDialog;
        }
        if (null == dialog) {
            return;
        }
        dialog.setCancelable(cancelable);
        if (dialog.isShowing()) {
            return;
        }
        dialog.show();
    }

    @Override
    public void closeProgress() {
        if (null == dialog) {
            return;
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void tip(@NonNull String msg) {
        tip(msg, TipEnum.Normal);
    }

    @Override
    public void tip(@NonNull String msg, TipEnum tipEnum) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        switch (tipEnum) {
            case Error:
            case Normal:

            case MsgBox:
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onDestroy() {
        closeProgress();
        mContext = null;
        dialog = null;
    }

}
