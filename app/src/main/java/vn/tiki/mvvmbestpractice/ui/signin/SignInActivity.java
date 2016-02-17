package vn.tiki.mvvmbestpractice.ui.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vn.tiki.mvvmbestpractice.R;
import vn.tiki.mvvmbestpractice.event.SignInEvent;

/**
 * Created by tale on 2/16/16.
 */
public class SignInActivity extends AppCompatActivity {

    private EventBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_page);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new SignInFragment())
                    .commit();
        }
        bus = EventBus.getDefault();

    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onEvent(SignInEvent event) {
        if (event.isSuccess()) {

        }
    }


}
