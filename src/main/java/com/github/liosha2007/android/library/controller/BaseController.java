package com.github.liosha2007.android.library.controller;

import android.os.Bundle;
import android.view.View;
import com.github.liosha2007.android.library.fragment.BaseFragment;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseController<T extends BaseFragment> {
    protected T fragment;

    public void initialize(View view, Bundle savedInstanceState, T fragment) {
        this.fragment = fragment;
    }

    public abstract void onViewCreated(Bundle savedInstanceState);

    /**
     * Will ba called when Fragment.onStart is called
     */
    public void onStart(){}

    /**
     * Will be called when fragment sets as current
     */
    public void onShowed() {}
}
