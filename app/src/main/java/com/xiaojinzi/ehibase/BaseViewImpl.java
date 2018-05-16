package com.xiaojinzi.ehibase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xiaojinzi.ehibase.inter.IBaseView;


/**
 * time   : 2018/03/14
 * blog   : http://blog.csdn.net/u011692041
 *
 * @author : xiaojinzi 30212
 */
public class BaseViewImpl implements IBaseView {

    @Nullable
    private Context mContext;

    public BaseViewImpl(@Nullable Context context) {
        mContext = context;
    }

    @Override
    public void showProgress() {
        showProgress(false);
    }

    @Override
    public void showProgress(boolean cancelable) {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void tip(@NonNull String msg) {
        tip(msg, TipEnum.Normal);
    }

    @Override
    public void tip(@NonNull String msg, TipEnum tipEnum) {

    }

    public void onDestroy() {

    }

}
