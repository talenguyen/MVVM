package vn.tiki.mvvmbestpractice.ui.signin;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by tale on 2/16/16.
 */
public class SignInViewModel {

    /**
     * Logic
     * 1. call sign must: show loading
     * 2. Bind result: if error -> show error or show success otherwise.
     */
    public ObservableBoolean processing = new ObservableBoolean();
    public ObservableBoolean error = new ObservableBoolean();
    public ObservableField<String> errorMessage = new ObservableField<>();

    public Observable<CharSequence> sigIn(CharSequence email, CharSequence pass) {
        return signInTask(email, pass)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        error.set(false);
                        processing.set(true);
                    }
                })
                .doOnNext(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        processing.set(false);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        processing.set(false);
                        error.set(true);
                        errorMessage.set(throwable.getMessage());
                    }
                });
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
