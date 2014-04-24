package com.github.liosha2007.android.library.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.github.liosha2007.android.library.application.ApplicationActivity;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @author Aleksey Permyakov
 */
public class FragmentManager extends FragmentPagerAdapter {
    private static final Logger LOGGER = Logger.getLogger(FragmentManager.class);
    private static LinkedHashMap<Integer, Fragment> key2fragments = new LinkedHashMap<Integer, Fragment>();
    public static FragmentManager adapter;
    private static ViewPager viewPager;

    protected FragmentManager(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    /**
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

    protected android.support.v4.app.FragmentManager fragmentManager() {
        return ApplicationActivity.activity.getSupportFragmentManager();
    }

    @Override
    public Fragment getItem(int i) {
        return Arrays.asList(key2fragments.values().toArray(new Fragment[key2fragments.size()]).clone()).get(i);
    }

    @Override
    public int getCount() {
        return key2fragments.size();
    }

    public void addFragment(int key, Fragment fragment) {
        key2fragments.put(key, fragment);
    }

    public void removeFragment(int key) {
        key2fragments.remove(key);
    }

    public int indexOf(int key) {
        return viewPager.getAdapter().getItemPosition(key2fragments.get(key));
    }

    /**
     * Set fragment as active using key
     * @param key fragment key
     */
    public void setCurrentItem(int key) {
        FragmentManager.viewPager.setCurrentItem(Arrays.asList(key2fragments.keySet().toArray(new Integer[key2fragments.size()])).indexOf(key));
    }
//
//    public int getCurrentItem(){
//        android.support.v4.app.FragmentManager fragmentManager = ApplicationActivity.activity.getSupportFragmentManager();
//        fr
//        return viewPager.getAdapter().getItemPosition(key2fragments.get(key))
//    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        FragmentManager.viewPager.setOnPageChangeListener(onPageChangeListener);
    }
}

//FragmentManager fm = getFragmentManager();
//fm.beginTransaction()
//        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
//        .show(somefrag)
//        .commit();
