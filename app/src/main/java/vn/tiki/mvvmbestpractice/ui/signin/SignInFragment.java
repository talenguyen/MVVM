package vn.tiki.mvvmbestpractice.ui.signin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import vn.tiki.mvvmbestpractice.R;
import vn.tiki.mvvmbestpractice.base.BaseFragment;
import vn.tiki.mvvmbestpractice.databinding.FragmentSigninBinding;
import vn.tiki.mvvmbestpractice.event.SignInEvent;
import vn.tiki.mvvmbestpractice.util.ThreadScheduler;

/**
 * Created by tale on 2/16/16.
 */
public class SignInFragment extends BaseFragment {
    private static final String TAG = "SignInFragment";
    private FragmentSigninBinding binding;
    private SignInViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signin, container, false);
        viewModel = new SignInViewModel(new ThreadScheduler(Schedulers.io(), AndroidSchedulers.mainThread()));
        binding.setViewModel(viewModel);
        binding.setHandler(this);
        bindLoading();
        return binding.getRoot();
    }

    private void bindLoading() {
        viewModel.processing.startWith(false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean loading) {
                        binding.from.setVisibility(loading ? View.GONE : View.VISIBLE);
                        binding.loading.setVisibility(loading ? View.VISIBLE :View.GONE);
                    }
                });
        viewModel.error.startWith(false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean error) {
                        binding.tvError.setVisibility(error ? View.VISIBLE : View.INVISIBLE);
                    }
                });
    }

    public void signIn(View view) {
        viewModel.signIn(binding.etEmail.getText(), binding.etPassword.getText())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        Log.d(TAG, "call onNext onThread: " + Thread.currentThread().getName());
                        EventBus.getDefault().post(new SignInEvent(true, charSequence.toString()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: SignIn errorField", throwable);
                    }
                });
    }

}
