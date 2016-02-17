package vn.tiki.mvvmbestpractice.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import vn.tiki.mvvmbestpractice.R;
import vn.tiki.mvvmbestpractice.ui.signin.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    private Subscription loadingSubscrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingSubscrition = Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        openSignIn();
                    }
                });
    }

    @Override
    protected void onPause() {
        if (loadingSubscrition != null && !loadingSubscrition.isUnsubscribed()) {
            loadingSubscrition.unsubscribe();
            loadingSubscrition = null;
        }
        super.onPause();
    }

    private void openSignIn() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}
