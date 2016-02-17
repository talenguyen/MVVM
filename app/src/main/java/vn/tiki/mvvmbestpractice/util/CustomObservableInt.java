package vn.tiki.mvvmbestpractice.util;

import android.annotation.SuppressLint;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by nongdenchet on 2/17/16.
 */
@SuppressLint("ParcelCreator")
public class CustomObservableInt extends ObservableInt {
    private List<Integer> mEmitValues;

    public CustomObservableInt() {
    }

    public CustomObservableInt(int value) {
        super(value);
    }

    @Override
    public void set(int value) {
        super.set(value);
        if (mEmitValues != null) {
            mEmitValues.add(value);
        }
    }

    public void startReceivedValues(@NonNull List<Integer> emitValues) {
        mEmitValues = emitValues;
        mEmitValues.clear();
    }
}
