package com.xiaojinzi.ehibase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaojinzi.ehibase.inter.IBaseView;
import com.xiaojinzi.ehibase.inter.ITipView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * modify by xiaojinzi
 * 类中的成员变量不会销毁,随着fragment的销毁而销毁,这个销毁和Fragment的生命周期销毁是两回事
 * 生命周期的销毁并不会导致这个类及其成员变量的销毁
 *
 * @author : HeQing 30212
 * @desc : fragment的基类
 * @time : 2017/12/15
 */
public abstract class IBaseFragment<T extends BasePresenter> extends Fragment implements IBaseView {

    /**
     * 标记的tag,Fragment的名字
     */
    protected String tag;

    /**
     * 当前fragment的presenter
     */
    protected T presenter;

    /**
     * 当前fragment的Context
     */
    protected Activity mContext;

    /**
     * 缓存当前的Fragment的视图
     */
    private View mContentView = null;

    /**
     * 管理所有的Disposable
     */
    protected final CompositeDisposable cd = new CompositeDisposable();

    @NonNull
    private BaseViewImpl baseViewImpl;

    public IBaseFragment() {
        tag = this.getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mContentView == null) {
            mContentView = getLayout(inflater, container, savedInstanceState);

            injectView(mContentView);

            init(mContentView);

            if (presenter != null) {
                presenter.onInit();
            }

        }

        return mContentView;
    }

    // 实现可以是ButterKnife的注入,上层不定义
    protected void injectView(View contentView){
        // ButterKnife.bind(this, contentView);
    }

    /**
     * 该方法会在{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}中调用
     *
     * @return 返回布局
     */
    public abstract View getLayout(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState);

    /**
     * 初始化，该方法会在{@link #onViewCreated(View, Bundle)}中调用
     * 用于初始化数据，比如构造presenter
     *
     * @param view
     */
    public void init(View view) {
        // empty
    }

    public void initData() {
        // empty
    }

    public void initListener() {
        // empty
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
    public void tip(@NonNull String msg, ITipView.TipEnum tipEnum) {
        getWrapView().tip(msg, tipEnum);
    }

    /**
     * after {@link #onStop()} and before {@link #onDestroy()}
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
        if (cd != null) {
            cd.dispose();
        }
        if (baseViewImpl != null) {
            baseViewImpl.onDestroy();
        }

    }

    private BaseViewImpl getWrapView() {
        if (baseViewImpl == null) {
            baseViewImpl = new BaseViewImpl(mContext);
        }
        return baseViewImpl;
    }

}
