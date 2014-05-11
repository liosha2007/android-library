package com.github.liosha2007.android.library.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;

/**
 * @author Aleksey Permyakov
 */
public class FragmentManager {
    private static final Logger LOGGER = Logger.getLogger(FragmentManager.class);
    private static final LinkedHashMap<Integer, Fragment> key2fragment = new LinkedHashMap<Integer, Fragment>();
    private static ViewPager viewPager = null;
    private static FragmentPageAdapter pagerAdapter = null;

    public static void onCreate(Bundle savedInstanceState, ViewPager viewPager, android.support.v4.app.FragmentManager supportFragmentManager) {
        pagerAdapter = new FragmentPageAdapter(supportFragmentManager);
        (FragmentManager.viewPager = viewPager).setAdapter(pagerAdapter);
    }

    public static void addFragment(int key, Fragment fragment) {
        key2fragment.put(key, fragment);
        pagerAdapter.add(fragment);
//        FragmentManager.viewPager.setAdapter(pagerAdapter);
    }

    public static void removeFragment(int key) {
        pagerAdapter.remove(key2fragment.get(key));
        key2fragment.remove(key);
//        FragmentManager.viewPager.setAdapter(pagerAdapter);
    }

    public static Fragment getCurrentFragment() {
        return pagerAdapter.getItem(viewPager.getCurrentItem());
    }

    public static void setCurrentFragment(int key) {
        viewPager.setCurrentItem(pagerAdapter.getItemPosition(key2fragment.get(key)), true);
    }


//    private static LinkedList<Fragment> fragments = new LinkedList<Fragment>();
//    public static FragmentManager adapter;
//    private static ViewPager viewPager;
//
//    protected FragmentManager(android.support.v4.app.FragmentManager fm) {
//        super(fm);
//    }
//
//    /**
//     * @param fragmentManager getSupportFragmentManager()
//     * @param viewPager
//     * @return
//     */
//    public static boolean initialize(android.support.v4.app.FragmentManager fragmentManager, ViewPager viewPager) {
//        if (fragmentManager == null) {
//            LOGGER.error("fragmentManager is null");
//            return false;
//        }
//        if (viewPager == null) {
//            LOGGER.error("viewPager is null");
//            return false;
//        }
//        FragmentManager.adapter = new FragmentManager(fragmentManager);
//        FragmentManager.viewPager = viewPager;
//
//        FragmentManager.viewPager.setAdapter(FragmentManager.adapter);
//        return true;
//    }
//
//    protected android.support.v4.app.FragmentManager fragmentManager() {
//        return ApplicationActivity.activity.getSupportFragmentManager();
//    }
//
//    @Override
//    public Fragment getItem(int i) {
//        return fragments.get(i);
//    }
//
//    @Override
//    public int getCount() {
//        return fragments.size();
//    }
//
//    public void addFragment(int key, Fragment view) {
//        FragmentTransaction transaction = fragmentManager().beginTransaction();
//        transaction.add(view, Integer.toString(key));
//        transaction.commit();
//        fragmentManager().executePendingTransactions();
//        //
//        fragments.add(view);
//    }
//
//    public void removeFragment(int key) {
//        Fragment view = fragmentManager().findFragmentByTag(Integer.toString(key));
//        ApplicationActivity.activity.getSupportFragmentManager()
//                .beginTransaction()
//                .remove(view)
//                .commit();
//        fragmentManager().executePendingTransactions();
//        //
//        fragments.remove(view);
//    }
//
//    public int indexOf(int key) {
////        Fragment view = fragmentManager().findFragmentByTag(Integer.toString(key));
////        return viewPager.getAdapter().getItemPosition(view);
//        Fragment view = fragmentManager().findFragmentByTag(Integer.toString(key));
//        return fragments.indexOf(view);
//    }
//
//    /**
//     * Set view as active using key
//     * @param key view key
//     */
//    public void setCurrentItem(int key) {
//        FragmentManager.viewPager.setCurrentItem(indexOf(key));
//    }
////
////    public int getCurrentItem(){
////        android.support.v4.app.FragmentManager fragmentManager = ApplicationActivity.activity.getSupportFragmentManager();
////        fr
////        return viewPager.getAdapter().getItemPosition(fragments.get(key))
////    }
//
//    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
//        FragmentManager.viewPager.setOnPageChangeListener(onPageChangeListener);
//    }
}

//FragmentManager fm = getFragmentManager();
//fm.beginTransaction()
//        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
//        .show(somefrag)
//        .commit();
