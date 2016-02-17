package vn.tiki.mvvmbestpractice.base;

import java.util.List;

/**
 * Created by tale on 2/16/16.
 */
public class LifeCycleEmitter {

    public void resume(List<LifeCycle> lifeCycles) {
        if (lifeCycles == null || lifeCycles.isEmpty()) {
            return;
        }
        for (LifeCycle lifeCycle : lifeCycles) {
            lifeCycle.onResume();
        }
    }

    public void pause(List<LifeCycle> lifeCycles) {
        if (lifeCycles == null || lifeCycles.isEmpty()) {
            return;
        }
        for (LifeCycle lifeCycle : lifeCycles) {
            lifeCycle.onPause();
        }
    }

}
