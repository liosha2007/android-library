package com.github.liosha2007.android.library.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.github.liosha2007.android.library.application.ApplicationActivity;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Aleksey Permyakov
 */
public class FragmentManager extends FragmentPagerAdapter {
    private static final Logger LOGGER = Logger.getLogger(FragmentManager.class);
    private static HashMap<Integer, Fragment> key2fragments = new HashMap<Integer, Fragment>();
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
        List<Fragment> fragments = Arrays.asList(key2fragments.values().toArray(new Fragment[key2fragments.size()]).clone());
        Collections.reverse(fragments);
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return key2fragments.size();
    }

    public void addFragment(int key, Fragment fragment) {
        key2fragments.put(key, fragment);

    }

    public void removeFragment(int key) {
        Fragment fragment = key2fragments.get(key);
        key2fragments.remove(key);


//        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager().beginTransaction();
//        fragmentTransaction.remove(fragmentManager().findFragmentById(fragment.getId())).commit();
    }

    public int indexOf(int key) {
        return viewPager.getAdapter().getItemPosition(key2fragments.get(key));
    }

    public void setCurrentItem(int itemIndex) {
        FragmentManager.viewPager.setCurrentItem(itemIndex);
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
