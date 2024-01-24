package com.byd.music.diff;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ThrottleFunction {
    private ObservableEmitter<Long> observableEmitter;

    public ThrottleFunction(long delayMillSeconds, @NonNull Runnable runnable) {
        Observable.create((ObservableOnSubscribe<Long>) emitter -> observableEmitter = emitter)
                .throttleLast(delayMillSeconds, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> runnable.run());
    }

    public void onExecuteNext() {
        if (observableEmitter != null) {
            observableEmitter.onNext(System.currentTimeMillis());
        }
    }
}
