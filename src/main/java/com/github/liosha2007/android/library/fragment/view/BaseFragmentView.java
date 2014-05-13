package com.github.liosha2007.android.library.fragment.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.fragment.controller.BaseFragmentController;
import org.apache.log4j.Logger;

/**
 * Created by liosha on 13.05.14.
 */
public class BaseFragmentView<T extends BaseFragmentController> {
    private static final Logger LOGGER = Logger.getLogger(BaseFragmentView.class);
    protected View view;
    protected T controller;
    private int layoutId;

    public BaseFragmentView(int layoutId) {
        this.layoutId = layoutId;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (this.view = inflater.inflate(this.layoutId, container, false));
    }

    public void onCreate() {

    }

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

    public void setController(T controller) {
        this.controller = controller;
    }
}