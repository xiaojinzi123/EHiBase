package com.xiaojinzi.ehibase.inter;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2018/02/01
 *
 */
@MainThread
public interface ITipView {

    /**
     * 信息提示的类型
     */
    enum TipEnum {
        Normal, Error, MsgBox
    }

    /**
     * 普通提示
     *
     * @param msg 提示的文本
     */
    void tip(@NonNull String msg);

    /**
     * 提示
     *
     * @param msg     提示的文本
     * @param tipEnum 提示的类型
     */
    void tip(@NonNull String msg, TipEnum tipEnum);

}
