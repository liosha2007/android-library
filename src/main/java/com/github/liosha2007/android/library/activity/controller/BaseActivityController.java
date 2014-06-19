package com.github.liosha2007.android.library.activity.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;

/**
 * @author Aleksey Permyakov
 */
public abstract class BaseActivityController<T extends BaseActivityView> extends FragmentActivity {
    protected T view;

    public BaseActivityController(T view){
        this.view = view;
        if (this.view != null){
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
     * @param storage to save application data
     */
    protected void onSaveData(Bundle storage) { }

    /**
     * Will be called when application should restore data
     * @param storage to restore data
     */
    public void onRestoreData(Bundle storage) { }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Will run Controller with null bundle object
     * @param clazz
     */
    public <T extends BaseActivityController> void run(Class<T> clazz){
        run(clazz, null);
    }

    /**
     * Will run Controller with bundle object
     * @param clazz
     * @param bundle
     */
    public <T extends BaseActivityController> void run(Class<T> clazz, Bundle bundle){
        Intent intent = new Intent(this, clazz);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        Utils.deb("Activity " + clazz.getSimpleName() + " is started!");
    }
}
