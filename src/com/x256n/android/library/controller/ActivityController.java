package com.x256n.android.library.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.x256n.android.library.common.Utils;
import com.x256n.android.library.view.ActivityView;

/**
 * @author liosha (22.02.2015)
 */
public abstract class ActivityController<V extends ActivityView> extends SlidingFragmentActivity {
    protected V view;

    @SuppressWarnings("unchecked")
    public ActivityController(V view) {
        this.view = view;
        this.view.setController(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        view.onCreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
    }

    /**
     * Override for customize layout
     * @return layout id
     */
    public int getRootLayout() {
        return view.getRootLayout();
    }


    /**
     * @param newActivityAnimation new activity animation resource
     */
    public void onBackPressed(int newActivityAnimation) {
        onBackPressed(newActivityAnimation, 0);
    }

    public void onBackPressed(int newActivityAnimation, int oldActivityAnimation) {
        super.onBackPressed();
        overridePendingTransition(newActivityAnimation, oldActivityAnimation);
    }

    public <A extends Activity> void run(Class<A> clazz){
        run(clazz, null, 0, 0);
    }

    public <A extends Activity> void run(Class<A> clazz, int newActivityAnimation, int oldActivityAnimation){
        run(clazz, null, newActivityAnimation, oldActivityAnimation);
    }

    public <A extends Activity> void run(Class<A> clazz, Bundle bundle){
        run(clazz, bundle, 0);
    }

    public <A extends Activity> void run(Class<A> clazz, Bundle bundle, int newActivityAnimation){
        run(clazz, bundle, newActivityAnimation, 0);
    }

    public <A extends Activity> void run(Class<A> clazz, int newActivityAnimation){
        run(clazz, null, newActivityAnimation, 0);
    }

    public  <A extends Activity> void run(Class<A> clazz, Bundle bundle, int newActivityAnimation, int oldActivityAnimation) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(newActivityAnimation, oldActivityAnimation);
        Utils.deb("Activity " + clazz.getSimpleName() + " is started!");
    }

    @Override
    protected void onDestroy() {
        view.onDestroy();
        view = null;
        super.onDestroy();
    }
}
