package com.xiaojinzi.ehibase.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Provides different types of schedulers.
 */
public class AndroidSchedulerProvider implements BaseSchedulerProvider {

    @Nullable
    private static AndroidSchedulerProvider INSTANCE;

    // Prevent direct instantiation.
    private AndroidSchedulerProvider() {
    }

    public static synchronized AndroidSchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AndroidSchedulerProvider();
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
