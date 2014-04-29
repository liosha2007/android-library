package com.github.liosha2007.android.library.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.controller.BaseController;
import org.apache.log4j.Logger;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseFragment<T extends BaseController> extends Fragment {
    private static final Logger LOGGER = Logger.getLogger(BaseFragment.class);
    protected View view;
    protected T controller;
    private int layoutId;

    protected BaseFragment(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (this.view = inflater.inflate(this.layoutId, container, false));
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.onStart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller.onViewCreated(savedInstanceState);
        onViewCreated(view);
    }

    public abstract void onViewCreated(View view);

    /**
     * Get View by ID
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T view(int id) {
        if (view == null) {
            LOGGER.error("view is null");
            return null;
        }
        return (T) Utils.view(view, id);
    }

    /**
     * Fix for bug
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (controller == null) {
            Utils.err("Error: Fragment created before controller!");
        } else {
            if (isVisibleToUser) {
                controller.onShow();
            } else {
                controller.onHide();
            }
        }
    }

    public Fragment setController(T controller) {
        this.controller = controller;
        return this;
    }
}
