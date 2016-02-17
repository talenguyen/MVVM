package vn.tiki.mvvmbestpractice.ui.signin;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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
    public void defaultFormVisibility() throws Exception {
        assertEquals(View.VISIBLE, viewModel.formVisibility.getValue().intValue());
    }

    @Test
    public void defaultProcessVisibility() throws Exception {
        assertEquals(View.GONE, viewModel.processVisibility.getValue().intValue());
    }

    @Test
    public void loadingWhenError() throws Exception {
        viewModel.processVisibility.subscribe(testSubscriberInt);
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        testSubscriberInt.assertReceivedOnNext(Arrays.asList(View.GONE, View.VISIBLE, View.GONE));
    }

    @Test
    public void loadingWhenSuccess() throws Exception {
        viewModel.processVisibility.subscribe(testSubscriberInt);
        viewModel.signIn("email", "123").toBlocking().single();
        testSubscriberInt.assertReceivedOnNext(Arrays.asList(View.GONE, View.VISIBLE, View.GONE));
    }

    @Test
    public void shouldThrowException() throws Exception {
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        testSubscriberChar.assertError(RuntimeException.class);
    }

    @Test
    public void formWhenError() throws Exception {
        viewModel.formVisibility.subscribe(testSubscriberInt);
        viewModel.signIn("email", "").subscribe(testSubscriberChar);
        testSubscriberInt.assertReceivedOnNext(Arrays.asList(View.VISIBLE, View.GONE, View.VISIBLE));
    }

    @Test
    public void formWhenSuccess() throws Exception {
        viewModel.formVisibility.subscribe(testSubscriberInt);
        viewModel.signIn("email", "123").toBlocking().single();
        testSubscriberInt.assertReceivedOnNext(Arrays.asList(View.VISIBLE, View.GONE, View.VISIBLE));
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