package vn.tiki.mvvmbestpractice.ui.signin;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;

/**
 * Created by tale on 2/17/16.
 */
public class SignInViewModelTest {
    private TestSubscriber<Integer> testSubscriberInt;
    private TestSubscriber<CharSequence> testSubscriberChar;
    private SignInViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        testSubscriberInt = TestSubscriber.create();
        testSubscriberChar = TestSubscriber.create();
        viewModel = new SignInViewModel();
    }

    @Test
    public void defaultErrorVisibility() throws Exception {
        assertEquals(View.GONE, viewModel.errorVisibility.get());
    }

    @Test
    public void loadingWhenError() throws Exception {
        viewModel.getProcessVisibility().subscribe(testSubscriberInt);
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        testSubscriberInt.assertValues(View.GONE, View.VISIBLE, View.GONE);
    }

    @Test
    public void loadingWhenSuccess() throws Exception {
        viewModel.getProcessVisibility().subscribe(testSubscriberInt);
        viewModel.signIn("email", "123").toBlocking().single();
        testSubscriberInt.assertValues(View.GONE, View.VISIBLE, View.GONE);
    }

    @Test
    public void shouldThrowException() throws Exception {
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        testSubscriberChar.assertError(RuntimeException.class);
    }

    @Test
    public void formWhenError() throws Exception {
        viewModel.getFormVisibility().subscribe(testSubscriberInt);
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        testSubscriberInt.assertValues(View.VISIBLE, View.GONE, View.VISIBLE);
    }

    @Test
    public void formWhenSuccess() throws Exception {
        viewModel.getFormVisibility().subscribe(testSubscriberInt);
        viewModel.signIn("email", "123").toBlocking().single();
        testSubscriberInt.assertValues(View.VISIBLE, View.GONE, View.VISIBLE);
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