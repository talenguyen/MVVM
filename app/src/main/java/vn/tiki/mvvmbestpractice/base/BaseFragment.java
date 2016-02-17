package vn.tiki.mvvmbestpractice.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tale on 2/16/16.
 */
public class BaseFragment extends Fragment {
    private List<LifeCycle> lifeCycles;
    private LifeCycleEmitter lifeCycleEmitter;

    public void bindLifeCycle(LifeCycle lifecycle) {
        lifeCycles.add(lifecycle);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifeCycles = new ArrayList<>();
        lifeCycleEmitter = new LifeCycleEmitter();
    }

    @Override
    public void onDestroyView() {
        lifeCycles.clear();
        lifeCycles = null;
        lifeCycleEmitter = null;
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeCycleEmitter.resume(lifeCycles);
    }

    @Override
    public void onPause() {
        lifeCycleEmitter.pause(lifeCycles);
        super.onPause();
    }
}
