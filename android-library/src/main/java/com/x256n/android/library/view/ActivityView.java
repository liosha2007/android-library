package com.x256n.android.library.view;

import android.view.View;

import com.x256n.android.library.controller.ActivityController;

import org.jetbrains.annotations.NotNull;

/**
 * @author liosha (22.02.2015)
 */
public abstract class ActivityView<C extends ActivityController> extends BaseActivityView<C> {
    protected C controller;
    protected final int layoutId;
    protected View view;

    public ActivityView(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setController(C controller) {
        this.controller = controller;
    }

    @Override
    public C getController() {
        return controller;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    public void onCreate(){
        controller.setContentView(layoutId);
        setView(controller.findViewById(android.R.id.content));
    }

    @NotNull
    @Override
    protected View getView() {
        return view;
    }

    @Override
    protected void setView(@NotNull View view) {
        this.view = view;
    }
}
