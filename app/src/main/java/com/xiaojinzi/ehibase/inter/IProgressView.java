package com.xiaojinzi.ehibase.inter;

import android.support.annotation.MainThread;

/**
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2018/02/01
 *
 */
@MainThread
public interface IProgressView {

    /**
     * 弹出加载框
     */
    void showProgress();

    /**
     * 弹出加载框
     *
     * @param cancelable 加载框是否可以取消
     */
    void showProgress(boolean cancelable);

    /**
     * 关闭对话框
     */
    void closeProgress();

}
