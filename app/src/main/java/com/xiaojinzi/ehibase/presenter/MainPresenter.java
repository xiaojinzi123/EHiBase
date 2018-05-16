package com.xiaojinzi.ehibase.presenter;

import android.support.annotation.NonNull;

import com.xiaojinzi.ehibase.base.presenter.BaseRxPresenter;
import com.xiaojinzi.ehibase.base.rx.RxSchedulerProvider;
import com.xiaojinzi.ehibase.base.util.NetWorkErrorException;
import com.xiaojinzi.ehibase.view.IMainView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * time   : 2018/05/16
 *
 * @author : xiaojinzi 30212
 */
public class MainPresenter extends BaseRxPresenter<IMainView> {

    public MainPresenter(@NonNull IMainView view) {
        super(view);
    }

    @Override
    public void onInit() {
        super.onInit();

        Observable<String> observable = Observable
                .just("String test")
                .observeOn(RxSchedulerProvider.getInstance().io())
                // 延时两秒,模拟数据
                .delay(2, TimeUnit.SECONDS)
                .observeOn(RxSchedulerProvider.getInstance().ui());

        subscribeCannotCancel(observable, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.showData(s);
            }
        });


    }


    public void onLoadDataFailTest() {

        Observable<String> observable = Observable
                .just("String test")
                .observeOn(RxSchedulerProvider.getInstance().io())
                // 延时两秒,模拟数据
                .delay(2, TimeUnit.SECONDS)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        throw new NetWorkErrorException("onLoadDataFailTest", 0);
                    }
                })
                .observeOn(RxSchedulerProvider.getInstance().ui());

        subscribeCannotCancel(observable, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });

    }

    public void onLoadDataSuccessTest() {

        Observable<String> observable = Observable
                .just("String test")
                .observeOn(RxSchedulerProvider.getInstance().io())
                // 延时两秒,模拟数据
                .delay(2, TimeUnit.SECONDS)
                .observeOn(RxSchedulerProvider.getInstance().ui());

        subscribeCannotCancel(observable, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.tip("onLoadDataSuccessTest：成功");
            }
        });

    }

}
