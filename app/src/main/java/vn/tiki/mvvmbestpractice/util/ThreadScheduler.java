package vn.tiki.mvvmbestpractice.util;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by tale on 2/18/16.
 */
public class ThreadScheduler {
    public final Scheduler subscribeOn;
    public final Scheduler observeOn;

    public ThreadScheduler(@NonNull Scheduler subscribeOn, @NonNull Scheduler observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public <T> Observable.Transformer<T, T> transformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> source) {
                return source.subscribeOn(subscribeOn)
                        .observeOn(observeOn);
            }
        };
    }
}
