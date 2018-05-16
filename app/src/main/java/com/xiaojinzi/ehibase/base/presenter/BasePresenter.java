package com.xiaojinzi.ehibase.base.presenter;

import android.support.annotation.NonNull;

import com.xiaojinzi.ehibase.base.view.inter.IBaseView;
import com.xiaojinzi.ehibase.base.util.ErrorUtil;
import com.xiaojinzi.ehibase.base.util.NetWorkErrorException;

/**
 * 这个可以针对任何的Observable进行订阅处理,除了定义的公共的异常,其他的异常请自行实现Consumer<Throwable>接口实现错误的逻辑
 * 这个Presenter只会配合{@link IBaseView}来增添代码,只写最最基础的代码
 *
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2017/12/06
 */
public class BasePresenter<T extends IBaseView> {

    protected String tag;

    @NonNull
    protected T view;

    public BasePresenter(@NonNull T view) {
        tag = getClass().getSimpleName();
        this.view = view;
    }

    /**
     * 会被 {@link android.app.Activity} or {@link android.support.v4.app.Fragment} 创建的时候调用
     * 你可以做一些初始化的事情
     */
    public void onInit() {
    }

    /**
     * 这个处理的方式问一下大家能不能接受--------------------------
     * 处理业务的时候的统一处理,这里是纯粹的处理错误的,不能添加其他不相关的代码,比如让视图关闭对话框这种
     *
     * @param t 错误
     * @return 返回一个值表示是否已经处理了
     */
    protected final boolean normalErrorSove(@NonNull Throwable t) {

        boolean b = onNormalErrorSove(t);

        if (b) {
            return b;
        }

        if (t instanceof NetWorkErrorException) {
            NetWorkErrorException netWorkErrorException = (NetWorkErrorException) t;
            view.tip(netWorkErrorException.getMessage());
            return true;
        } else if (ErrorUtil.isNetWorkError(t)) {
            view.tip("网络异常,请检查您的网络", IBaseView.TipEnum.Error);
            return true;
        } else if (ErrorUtil.isServerError(t)) {
            view.tip("服务器异常", IBaseView.TipEnum.Error);
            return true;
        } else {
            view.tip("请求异常，请稍后重试", IBaseView.TipEnum.Error);
        }

        return false;

    }

    /**
     * 给子类一个处理基础错误的机会
     *
     * @param t
     * @return
     */
    protected boolean onNormalErrorSove(@NonNull Throwable t) {
        // empty
        return false;
    }

    public void onDestroy() {
    }

}
