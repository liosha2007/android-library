package com.github.liosha2007.android.library.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * @author Aleksey Permyakov
 */
public class FragmentManager extends FragmentPagerAdapter {
    private static final Logger LOGGER = Logger.getLogger(FragmentManager.class);
    private static HashMap<Integer, Fragment> key2fragments = new HashMap<Integer, Fragment>();
    private static FragmentManager adapter;
    private static ViewPager viewPager;

    protected FragmentManager(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    /**
     *
     *
     * @param fragmentManager getSupportFragmentManager()
     * @param viewPager
     * @return
     */
    public static boolean initialize(android.support.v4.app.FragmentManager fragmentManager, ViewPager viewPager) {
        if (fragmentManager == null) {
            LOGGER.error("fragmentManager is null");
            return false;
        }
        if (viewPager == null) {
            LOGGER.error("viewPager is null");
            return false;
        }
        FragmentManager.adapter = new FragmentManager(fragmentManager);
        FragmentManager.viewPager = viewPager;

        FragmentManager.viewPager.setAdapter(FragmentManager.adapter);
        return true;
    }

    @Override
    public Fragment getItem(int i) {
        return key2fragments.values().toArray(new Fragment[key2fragments.size()])[i];
    }

    @Override
    public int getCount() {
        return key2fragments.size();
    }

    public void setCurrentItem(int itemIndex){
        FragmentManager.viewPager.setCurrentItem(itemIndex);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener){
        FragmentManager.viewPager.setOnPageChangeListener(onPageChangeListener);
    }
}
