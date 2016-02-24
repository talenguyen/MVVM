package vn.tiki.mvvmbestpractice.ui.signin;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

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
    public void testProcessingField_shouldBeFalseByDefault() throws Exception {
        Assert.assertEquals(false, viewModel.processing.get());
    }

    @Test
    public void testProcessingField_shouldChangeWhenShowOrHideLoading() throws Exception {
        // should be true when show loading
        viewModel.showLoading();
        Assert.assertEquals(true, viewModel.processing.get());
        // should be false when hide loading
        viewModel.hideLoading();
        Assert.assertEquals(false, viewModel.processing.get());
    }

    @Test
    public void testSignIn_shouldShowThenHideLoading() {
        final SignInViewModel signInViewModelSpy = Mockito.spy(viewModel);
        signInViewModelSpy.signIn("giang", "123").subscribe();
        InOrder inOrder = Mockito.inOrder(signInViewModelSpy);
        inOrder.verify(signInViewModelSpy).showLoading();
        inOrder.verify(signInViewModelSpy).hideLoading();
    }
}