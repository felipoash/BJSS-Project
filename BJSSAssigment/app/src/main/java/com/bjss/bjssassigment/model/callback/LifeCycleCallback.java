package com.bjss.bjssassigment.model.callback;

import android.app.Activity;
import android.support.annotation.Nullable;

/**
 * Created by Filippo-TheAppExpert on 7/16/2015.
 */
public interface LifeCycleCallback {

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityCreated(@Nullable Activity activity);
}
