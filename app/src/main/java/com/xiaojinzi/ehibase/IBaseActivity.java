package com.xiaojinzi.ehibase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.xiaojinzi.ehibase.inter.IBaseView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 这个基类添加功能请慎重,如果不是全部Activity都会具备的功能
 * 请通过拓展实现
 *
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2017/12/07
 *
 */
public abstract class IBaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    /**
     * 标记的tag,Activity的名字
     */
    protected String tag;

    /**
     * 上下文
     */
    protected Activity mContext;

    /**
     * presenter
     */
    protected T presenter;

    /**
     * 管理所有的Disposable
     */
    protected final CompositeDisposable cd = new CompositeDisposable();

    private BaseViewImpl baseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;

        super.onCreate(savedInstanceState);

        initBase();

        setContentView(getView(mContext));

        // 初始化
        init();

        initData();

        // 初始化监听事件
        initListener();

        if (presenter != null) {
            presenter.onInit();
        }

    }

    protected abstract View getView(Context context);

    protected void initBase() {

        // init tag
        tag = this.getClass().getSimpleName();

        mContext = this;

        // 如果使用沉浸式状态栏
        if (isTranslucentNavigation()) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    /**
     * 默认使用沉浸式状态栏
     *
     * @return
     */
    protected boolean isTranslucentNavigation() {
        return true;
    }

    public void init() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        injectView();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        injectView();
    }

    // 实现可以是ButterKnife的注入,上层不定义
    protected void injectView(){
        // unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unSubscribe();
        }
        if (cd != null) {
            cd.dispose();
        }
        if (baseView != null) {
            baseView.onDestroy();
        }
    }

    @Override
    public void showProgress() {
        getWrapView().showProgress();
    }

    @Override
    public void showProgress(boolean cancelable) {
        getWrapView().showProgress(cancelable);
    }

    @Override
    public void closeProgress() {
        getWrapView().closeProgress();
    }

    @Override
    public void tip(@NonNull String msg) {
        getWrapView().tip(msg);
    }

    @Override
    public void tip(@NonNull String msg, TipEnum tipEnum) {
        getWrapView().tip(msg, tipEnum);
    }

    private BaseViewImpl getWrapView() {
        if (baseView == null) {
            baseView = new BaseViewImpl(mContext);
        }
        return baseView;
    }

}
