package com.x256n.android.library.fragment.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.x256n.android.library.common.Utils;
import com.x256n.android.library.fragment.view.FragmentView;

/**
 * @author liosha (13.02.2015)
 */
public abstract class FragmentController<T extends FragmentView> extends Fragment {
    protected T view;

    protected FragmentController(T view) {
        this.view = view;
        if (this.view != null) {
            this.view.setController(this);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onShow();
        } else {
            onHide();
        }
    }

    public void onShow() {
    }

    public void onHide() {
    }

    /**
     * Will run Controller with null bundle object
     *
     * @param clazz
     */
    public <T extends Activity> void run(Class<T> clazz) {
        run(clazz, null);
    }

    /**
     * Will run Controller with bundle object
     *
     * @param clazz
     * @param bundle
     */
    public <T extends Activity> void run(Class<T> clazz, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        Utils.deb("Activity " + clazz.getSimpleName() + " is started!");
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    }

    public <T extends FragmentController> T withArguments(Bundle args) {
        super.setArguments(args);
        return (T) this;
    }
}