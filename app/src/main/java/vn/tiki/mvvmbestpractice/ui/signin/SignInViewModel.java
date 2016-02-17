package vn.tiki.mvvmbestpractice.ui.signin;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.subjects.BehaviorSubject;

/**
 * Created by tale on 2/16/16.
 */
public class SignInViewModel {
    public ObservableInt errorVisibility = new ObservableInt(View.GONE);
    public ObservableField<String> errorMessage = new ObservableField<>();
    public BehaviorSubject<Integer> formVisibility = BehaviorSubject.create(View.VISIBLE);
    public BehaviorSubject<Integer> processVisibility = BehaviorSubject.create(View.GONE);

    private void setErrorVisibility(boolean hasError) {
        errorVisibility.set(hasError ? View.VISIBLE : View.GONE);
    }

    private void setProcessVisibility(boolean showProgress) {
        processVisibility.onNext(showProgress ? View.VISIBLE : View.GONE);
        formVisibility.onNext(showProgress ? View.GONE : View.VISIBLE);
    }

    public Observable<CharSequence> signIn(CharSequence email, CharSequence pass) {
        return signInTask(email, pass)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        setErrorVisibility(false);
                        setProcessVisibility(true);
                    }
                })
                .doOnNext(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        setProcessVisibility(false);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setProcessVisibility(false);
                        setErrorVisibility(true);
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
