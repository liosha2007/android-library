package com.github.liosha2007.android.library.activity.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.database.DaoFactory;
import com.j256.ormlite.dao.BaseDaoImpl;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseActivityController<T extends BaseActivityView> extends FragmentActivity {
    protected T view;

    public BaseActivityController(T view) {
        this.view = view;
        if (this.view != null) {
            this.view.setController(this);
        }
    }

    protected void onCreate() {
    }

    @Override
    protected void onCreate(Bundle storage) {
        super.onCreate(storage);
        view.onCreate();
        this.onCreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle storage) {
        super.onSaveInstanceState(storage);
        view.onSaveData(storage);
        this.onSaveData(storage);
    }

    @Override
    protected void onRestoreInstanceState(Bundle storage) {
        super.onRestoreInstanceState(storage);
        view.onRestoreData(storage);
        this.onRestoreData(storage);
    }

    /**
     * Will be called when application should save data
     *
     * @param storage to save application data
     */
    protected void onSaveData(Bundle storage) {
    }

    /**
     * Will be called when application should restore data
     *
     * @param storage to restore data
     */
    public void onRestoreData(Bundle storage) {
    }

    public <T extends BaseDaoImpl> T daoFor(Class clazz) {
        return DaoFactory.daoFor(this, clazz);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * @param newActivityAnimation new activity animation resource
     */
    public void onBackPressed(int newActivityAnimation) {
        onBackPressed(newActivityAnimation, 0);
    }

    /**
     * @param newActivityAnimation new activity animation resource
     * @param oldActivityAnimation old activity animation resource
     */
    public void onBackPressed(int newActivityAnimation, int oldActivityAnimation) {
        super.onBackPressed();
        overridePendingTransition(newActivityAnimation, oldActivityAnimation);
    }

    /**
     * Will run Controller with null bundle object
     *
     * @param clazz
     */
    public <T extends BaseActivityController> void run(Class<T> clazz) {
        run(clazz, null);
    }

    /**
     * Will run Controller with null bundle object
     *
     * @param clazz
     * @param newActivityAnimation new activity animation resource
     */
    public <T extends BaseActivityController> void run(Class<T> clazz, int newActivityAnimation) {
        run(clazz, null, newActivityAnimation);
    }

    /**
     * Will run Controller with null bundle object
     *
     * @param clazz
     * @param newActivityAnimation new activity animation resource
     * @param oldActivityAnimation old activity animation resource
     */
    public <T extends BaseActivityController> void run(Class<T> clazz, int newActivityAnimation, int oldActivityAnimation) {
        run(clazz, null, newActivityAnimation, oldActivityAnimation);
    }

    /**
     * Will run Controller with bundle object
     *
     * @param clazz
     * @param bundle
     */
    public <T extends BaseActivityController> void run(Class<T> clazz, Bundle bundle) {
        run(clazz, bundle, 0);
    }

    /**
     * Will run Controller with bundle object
     * Intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); to disable standard animation
     *
     * @param clazz
     * @param bundle
     * @param newActivityAnimation new activity animation resource
     */
    private <T extends BaseActivityController> void run(Class<T> clazz, Bundle bundle, int newActivityAnimation) {
        run(clazz, bundle, newActivityAnimation, 0);
    }

    /**
     * Will run Controller with null bundle object
     *
     * @param clazz
     * @param bundle
     * @param newActivityAnimation new activity animation resource
     * @param oldActivityAnimation old activity animation resource
     */
    private <T extends BaseActivityController> void run(Class<T> clazz, Bundle bundle, int newActivityAnimation, int oldActivityAnimation) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(newActivityAnimation, oldActivityAnimation);
        Utils.deb("Activity " + clazz.getSimpleName() + " is started!");
    }
}
