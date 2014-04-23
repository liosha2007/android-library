package com.github.liosha2007.android.library.application;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;

/**
 * @author Aleksey Permyakov
 */
public abstract class ApplicationActivity extends FragmentActivity {
    public static ApplicationActivity activity;

    protected static ViewPager viewPager;
    protected static IBackPressed backPressed;
    private int _mainLayout;
    private int _viewPager;

    protected ApplicationActivity(int mainLayout, int viewPager) {
        super();
        _mainLayout = mainLayout;
        _viewPager = viewPager;
        if (activity != null) {
            Utils.err("activity created more that one time");
        }
        activity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity.setContentView(_mainLayout);
        FragmentManager.initialize(activity.getSupportFragmentManager(), (viewPager = Utils.view(activity, _viewPager)));
        onFragmentCreate(FragmentManager.adapter);
    }

    protected abstract void onFragmentCreate(FragmentManager adapter);

    /**
     *
     */
    @Override
    public void onBackPressed() {
        if (backPressed != null && backPressed.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * @return
     */
    public static IBackPressed getBackPressed() {
        return backPressed;
    }

    /**
     * @param backPressed
     */
    public static void setBackPressed(IBackPressed backPressed) {
        ApplicationActivity.backPressed = backPressed;
    }
}
