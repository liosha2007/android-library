package com.github.liosha2007.android.library.application;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import org.apache.log4j.Logger;

/**
 * @author Aleksey Permyakov
 */
public class ApplicationActivity extends FragmentActivity {
    private static final Logger LOGGER = Logger.getLogger(ApplicationActivity.class);

    public static ApplicationActivity activity;

    protected static ViewPager viewPager;
    protected static IBackPressed backPressed;

    public ApplicationActivity(){
        super();
        if (activity != null) {
            LOGGER.error("activity created more that one time");
        }
        activity = this;
    }

    /**
     *
     * @return
     */
    public static final boolean initialize(int viewerPagerId){
        if (activity == null){
            return false;
        }
        viewPager = Utils.view(activity, viewerPagerId);
        FragmentManager.initialize(activity.getSupportFragmentManager(), viewPager);
        return true;
    }

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
     *
     * @return
     */
    public static IBackPressed getBackPressed() {
        return backPressed;
    }

    /**
     *
     * @param backPressed
     */
    public static void setBackPressed(IBackPressed backPressed) {
        ApplicationActivity.backPressed = backPressed;
    }
}
