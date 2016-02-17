package vn.tiki.mvvmbestpractice.util;

import android.annotation.SuppressLint;
import android.databinding.ObservableInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nongdenchet on 2/17/16.
 */
@SuppressLint("ParcelCreator")
public class CustomObservableInt extends ObservableInt {
    private List<Integer> mEmitValues = new ArrayList<>();

    public CustomObservableInt() {
    }

    public CustomObservableInt(int value) {
        super(value);
        mEmitValues.add(value);
    }

    public List<Integer> getEmitValues() {
        return mEmitValues;
    }

    @Override
    public void set(int value) {
        super.set(value);
        mEmitValues.add(value);
    }
}
