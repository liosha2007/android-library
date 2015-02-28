package com.x256n.android.library.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.x256n.android.library.view.ActivityView;

/**
 * @author liosha (22.02.2015)
 */
public abstract class ActivityController<V extends ActivityView> extends BaseActivityController<ActivityView> {
    protected final V view;

    @SuppressWarnings("unchecked")
    public ActivityController(V view) {
        this.view = view;
        this.view.setController(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        view.onCreate();
    }

    /**
     * Override for customize layout
     * @return layout id
     */
    public int getRootLayout() {
        return view.getRootLayout();
    }

    @Override
    public ActivityView getView() {
        return view;
    }
}
