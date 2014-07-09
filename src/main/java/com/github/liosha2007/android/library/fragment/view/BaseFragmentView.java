package com.github.liosha2007.android.library.fragment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.controller.BaseFragmentController;

/**
 * @author liosha on 13.05.14.
 */
public class BaseFragmentView<T extends BaseFragmentController> {
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
            Log.e(Utils.TAG, "view is null");
            return null;
        }
        return (T) Utils.view(view, id);
    }

    public void setController(T controller) {
        this.controller = controller;
    }
}
