package vn.tiki.mvvmbestpractice.ui.signin;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import vn.tiki.mvvmbestpractice.util.ThreadScheduler;

/**
 * Created by tale on 2/17/16.
 */
public class SignInViewModelTest {

    private SignInViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new SignInViewModel(new ThreadScheduler(Schedulers.immediate(), Schedulers.immediate()));
    }

    @Test
    public void shouldShowLoading() {
        TestSubscriber<Boolean> objectTestObserver = TestSubscriber.create();
        viewModel.processing.subscribe(objectTestObserver);
        viewModel.signIn("giang", "123").subscribe();
        objectTestObserver.assertValues(true, false);
    }
}