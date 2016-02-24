package vn.tiki.mvvmbestpractice.ui.signin;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.subjects.BehaviorSubject;
import vn.tiki.mvvmbestpractice.util.ThreadScheduler;

/**
 * Created by tale on 2/16/16.
 */
public class SignInViewModel {
    /**
     * Logic
     * 1. call sign must: show loading
     * 2. Bind result: if error -> show error or show success otherwise.
     */
    public final ObservableBoolean processing = new ObservableBoolean(false);
    public BehaviorSubject<Boolean> error = BehaviorSubject.create();
    public ObservableField<String> errorMessage = new ObservableField<>();

    private final ThreadScheduler threadScheduler;

    public SignInViewModel(ThreadScheduler threadScheduler) {
        this.threadScheduler = threadScheduler;
    }

    public Observable<CharSequence> signIn(CharSequence email, CharSequence pass) {
        showLoading();
        return signInTask(email, pass)
                .compose(threadScheduler.<CharSequence>transformer())
                .doOnNext(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        hideLoading();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoading();
                        showError(throwable);
                    }
                });
    }

    private void showError(Throwable throwable) {
        error.onNext(true);
        errorMessage.set(throwable.getMessage());
    }

    void hideLoading() {
        processing.set(false);
    }

    void showLoading() {
        error.onNext(false);
        processing.set(true);
    }

    private Observable<CharSequence> signInTask(final CharSequence email, final CharSequence pass) {
        return rx.Observable.defer(new Func0<rx.Observable<CharSequence>>() {
            @Override
            public rx.Observable<CharSequence> call() {
                try {
                    Thread.sleep(2000);
                    if (email.length() > 0 && "123".equals(pass.toString())) {
                        return Observable.just(email);
                    } else {
                        return Observable.error(new RuntimeException("email or password not matched"));
                    }
                } catch (InterruptedException e) {
                    return Observable.error(e);
                }
            }
        });
    }
}
