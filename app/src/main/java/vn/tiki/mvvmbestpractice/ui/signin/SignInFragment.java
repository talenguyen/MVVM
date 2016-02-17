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
        viewModel = new SignInViewModel();
        binding.setViewModel(viewModel);
        binding.setHandler(this);
        return binding.getRoot();
    }

    public void signIn(View view) {
        viewModel.sigIn(binding.etEmail.getText(), binding.etPassword.getText())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        EventBus.getDefault().post(new SignInEvent(true, charSequence.toString()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: SignIn error", throwable);
                    }
                });
    }

}
