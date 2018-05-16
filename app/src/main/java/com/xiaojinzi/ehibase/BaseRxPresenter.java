package com.xiaojinzi.ehibase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xiaojinzi.ehibase.inter.IBaseView;
import com.xiaojinzi.ehibase.rx.RxSchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 这个可以针对任何的Observable进行订阅处理,除了定义的公共的异常,其他的异常请自行实现Consumer<Throwable>接口实现错误的逻辑
 * 这个Presenter只会配合{@link IBaseView}来增添代码,只写最最基础的代码
 *
 * @author : xiaojinzi 30212
 * @desc :
 * @time : 2017/12/06
 */
public class BaseRxPresenter<T extends IBaseView>  extends BasePresenter<T>{


    public BaseRxPresenter(@NonNull T view) {
        super(view);
    }

    /**
     * 管理所有的Disposable
     */
    protected final CompositeDisposable compositeSubscription = new CompositeDisposable();

    // ==========================================没有弹框==========================start

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success) {
        subscribe(o, success, null, null, false, false);
    }

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull Consumer<Throwable> fail) {
        subscribe(o, success, fail, null, false, false);
    }

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success,
                                               @NonNull Consumer<Throwable> fail, @NonNull final Action complete) {
        subscribe(o, success, fail, complete, false, false);
    }

    protected final <T> void subscribeNoDialog(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull final Action complete) {
        subscribe(o, success, null, complete, false, false);
    }

    // ==========================================没有弹框==========================end

    // ==========================================不能取消弹框==========================start

    protected final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success) {
        subscribe(o, success, null, null, true, false);
    }

    protected final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull Consumer<Throwable> fail) {
        subscribe(o, success, fail, null, true, false);
    }

    protected final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull final Action complete) {
        subscribe(o, success, null, complete, true, false);
    }

    protected final <T> void subscribeCannotCancel(@NonNull Observable<T> o, @NonNull Consumer<T> success, @NonNull Consumer<Throwable> fail, @NonNull final Action complete) {
        subscribe(o, success, fail, complete, true, false);
    }

    // ==========================================不能取消弹框==========================end


    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success) {
        subscribe(o, success, null, null, true, true);
    }


    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success, @Nullable Consumer<Throwable> fail) {
        subscribe(o, success, fail, null, true, true);
    }


    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success, final boolean isShowDialog) {
        subscribe(o, success, null, null, isShowDialog, true);
    }


    protected final <T> void subscribe(@NonNull Observable<T> o, @NonNull Consumer<T> success, @Nullable Consumer<Throwable> fail, final boolean isShowDialog) {
        subscribe(o, success, fail, null, isShowDialog, true);
    }

    protected final <T> void subscribe(@NonNull Observable<T> o,
                                       @NonNull final Consumer<T> success,
                                       @Nullable final Consumer<Throwable> fail,
                                       @Nullable final Action complete,
                                       final boolean isShowDialog,
                                       final boolean cancelable) {

        final Consumer<T> mAccept = new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                success.accept(t);
            }
        };

        final Consumer<Throwable> mError = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception { // 出错处理
                if (view != null && isShowDialog) {
                    view.closeProgress();
                }
                if (fail != null) {
                    fail.accept(t);
                } else { // default
                    normalErrorSove(t);
                }
            }
        };

        final Action mComplete = new Action() { // 关闭菊花
            @Override
            public void run() throws Exception {
                if (view != null && isShowDialog) {
                    view.closeProgress();
                }
                if (complete != null) {
                    complete.run();
                }
            }
        };

        Disposable disposable = o
                .observeOn(RxSchedulerProvider.getInstance().ui())
                .subscribeOn(RxSchedulerProvider.getInstance().io())
                // 订阅的时候弹出菊花
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (view != null && isShowDialog) {
                            view.showProgress(cancelable);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (view != null && isShowDialog) {
                            view.closeProgress();
                        }
                    }
                })
                .subscribeOn(RxSchedulerProvider.getInstance().ui())
                .subscribe(mAccept, mError, mComplete);

        compositeSubscription.add(disposable);

    }

    /**
     * 取消注册
     */
    public void unSubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

}
