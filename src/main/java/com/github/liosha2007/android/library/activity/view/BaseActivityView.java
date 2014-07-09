package com.github.liosha2007.android.library.activity.view;

import android.os.Bundle;
import android.view.View;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import org.apache.log4j.Logger;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseActivityView<T extends BaseActivityController> {
    private static final Logger LOGGER = Logger.getLogger(BaseActivityView.class);
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
            LOGGER.error("view is null");
            return null;
        }
        return (T) Utils.view(view, id);
    }

    /**
     * Will be called when application should save data
     * @param storage to save application data
     */
    public void onSaveData(Bundle storage) {
        storage.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    }

    /**
     * Will be called when application should restore data
     * @param storage to restore data
     */
    public void onRestoreData(Bundle storage) { }

    public void setController(T controller) {
        this.controller = controller;
    }
}
