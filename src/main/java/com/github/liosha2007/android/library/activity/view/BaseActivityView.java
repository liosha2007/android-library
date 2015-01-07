package com.github.liosha2007.android.library.activity.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseActivityView<T extends BaseActivityController> {
    protected View view;
    protected T controller;
    private int layoutId;

    public BaseActivityView(int layoutId) {
        this.layoutId = layoutId;
    }

    public void onCreate() {
        controller.setContentView(layoutId);
        view = controller.findViewById(android.R.id.content);
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

    /**
     * Inflate layout
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T inflate(int id) {
        return (T) controller.getLayoutInflater().inflate(id, null);
    }

    /**
     * Will be called when application should save data
     *
     * @param storage to save application data
     */
    public void onSaveData(Bundle storage) {
        storage.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    }

    /**
     * Will be called when application should restore data
     *
     * @param storage to restore data
     */
    public void onRestoreData(Bundle storage) {
    }

    public void setController(T controller) {
        this.controller = controller;
    }
}
