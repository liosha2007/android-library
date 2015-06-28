package com.x256n.android.library.fragment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.x256n.android.library.common.Utils;
import com.x256n.android.library.fragment.controller.FragmentController;

/**
 * @author liosha (13.02.2015)
 */
public abstract class FragmentView<T extends FragmentController> {
    protected View view;
    protected T controller;
    private int layoutId;

    public FragmentView(int layoutId) {
        this.layoutId = layoutId;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        this.view = inflater.inflate(this.layoutId, container, false);
        return this.view;
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
    @Deprecated
    public <T extends View> T view(int id) {
        if (view == null) {
            Log.e(Utils.LOG_TAG, "view is null");
            return null;
        }
        return (T) Utils.view(view, id);
    }

    public <T extends View> boolean view(int resourceId, Class<T> clazz, Utils.IViewSuccess<T> successCallback, Utils.IViewFail failCallback) {
        return Utils.view(view, resourceId, clazz, successCallback, failCallback);
    }

    public <T extends View> boolean view(int resourceId, Class<T> clazz, Utils.IViewSuccess<T> successCallback) {
        return Utils.view(view, resourceId, clazz, successCallback);
    }

    public <T extends View> boolean view(int resourceId, Utils.IViewSuccess<T> successCallback) {
        return Utils.view(view, resourceId, null, successCallback);
    }

    public void setController(T controller) {
        this.controller = controller;
    }


    /**
     * Inflate layout
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T inflate(int id) {
        return (T) controller.getLayoutInflater(controller.getArguments()).inflate(id, null);
    }

}