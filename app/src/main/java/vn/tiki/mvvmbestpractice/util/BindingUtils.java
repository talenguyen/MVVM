package vn.tiki.mvvmbestpractice.util;

import android.view.View;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by nongdenchet on 2/17/16.
 */
public class BindingUtils {

    /**
     * bind visibility to a view
     * @param view : view to bind
     * @param visibility : observable emit visibility
     */
    public static <T> void bindVisibility(final View view, Observable<Integer> visibility) {
        visibility.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer value) {
                        view.setVisibility(value);
                    }
                });
    }
}
