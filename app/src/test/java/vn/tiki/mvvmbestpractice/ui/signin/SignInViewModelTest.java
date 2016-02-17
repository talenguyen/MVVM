package vn.tiki.mvvmbestpractice.ui.signin;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by tale on 2/17/16.
 */
public class SignInViewModelTest {
    private TestSubscriber<CharSequence> testSubscriberChar;
    private SignInViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        testSubscriberChar = TestSubscriber.create();
        viewModel = new SignInViewModel();
    }

    @Test
    public void defaultErrorVisibility() throws Exception {
        assertEquals(View.GONE, viewModel.errorVisibility.get());
    }

    @Test
    public void defaultFormVisibility() throws Exception {
        assertEquals(View.VISIBLE, viewModel.formVisibility.get());
    }

    @Test
    public void defaultProcessVisibility() throws Exception {
        assertEquals(View.GONE, viewModel.processVisibility.get());
    }

    @Test
    public void loadingWhenError() throws Exception {
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
    }

    @Test
    public void loadingWhenSuccess() throws Exception {
        viewModel.signIn("email", "123").toBlocking().single();
        assertThat(viewModel.formVisibility.getEmitValues()).contains(View.GONE, View.VISIBLE, View.GONE);
    }

    @Test
    public void shouldThrowException() throws Exception {
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        testSubscriberChar.assertError(RuntimeException.class);
    }

    @Test
    public void formWhenError() throws Exception {
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        assertThat(viewModel.formVisibility.getEmitValues()).contains(View.VISIBLE, View.GONE, View.VISIBLE);
    }

    @Test
    public void formWhenSuccess() throws Exception {
        viewModel.signIn("email", "123").toBlocking().single();
        assertThat(viewModel.formVisibility.getEmitValues()).contains(View.VISIBLE, View.GONE, View.VISIBLE);
    }

    @Test
    public void shouldShowError() throws Exception {
        viewModel.signIn("", "123").subscribe(testSubscriberChar);
        assertEquals(View.VISIBLE, viewModel.errorVisibility.get());
        assertEquals("email or password not matched", viewModel.errorMessage.get());
    }

    @Test
    public void shouldSignInSuccess() throws Exception {
        CharSequence email = viewModel.signIn("email", "123").toBlocking().single();
        assertEquals(View.GONE, viewModel.errorVisibility.get());
        assertEquals("email", email);
    }
}