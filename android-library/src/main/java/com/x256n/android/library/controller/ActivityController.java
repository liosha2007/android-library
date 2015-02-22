package com.x256n.android.library.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.x256n.android.library.view.ActivityView;

/**
 * @author liosha (22.02.2015)
 */
public abstract class ActivityController<V extends ActivityView> extends BaseActivityController<ActivityView, ActivityController> {
    private final V view;

    @SuppressWarnings("unchecked")
    public ActivityController(V view) {
        this.view = view;
        this.view.setController(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        view.onCreate();
    }

    @Override
    public ActivityView getView() {
        return view;
    }
}
