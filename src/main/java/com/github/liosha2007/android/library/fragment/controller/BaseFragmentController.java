package com.github.liosha2007.android.library.fragment.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.database.DaoFactory;
import com.github.liosha2007.android.library.fragment.view.BaseFragmentView;
import com.j256.ormlite.dao.BaseDaoImpl;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseFragmentController<T extends BaseFragmentView> extends Fragment {
    protected T view;

    protected BaseFragmentController(T view) {
        this.view = view;
        if (this.view != null) {
            this.view.setController(this);
        }
    }

    protected void onCreate() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view.onCreate();
        this.onCreate();
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


    public <T extends BaseDaoImpl> T daoFor(Class clazz) {
        return DaoFactory.daoFor(getActivity(), clazz);
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

    public <T extends BaseFragmentController> T withArguments(Bundle args) {
        super.setArguments(args);
        return (T) this;
    }
}
