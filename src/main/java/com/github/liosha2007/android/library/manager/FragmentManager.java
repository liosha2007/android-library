package com.github.liosha2007.android.library.manager;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.controller.BaseFragmentController;
import com.github.liosha2007.android.library.fragment.view.BaseFragmentView;

import java.util.HashMap;

/**
 * @author Aleksey Permyakov
 */
public class FragmentManager <T extends BaseFragmentView> {
    protected static final HashMap<ViewPager, FragmentManager> fragmentPager2FragmentManager = new HashMap<ViewPager, FragmentManager>();
    private ViewPager viewPager;
    private FragmentPageAdapter fragmentPageAdapter;

    protected FragmentManager(ViewPager viewPager, android.support.v4.app.FragmentManager fragmentManager, BaseFragmentController<T>[] fragments){
        this.viewPager = viewPager;
        this.fragmentPageAdapter = new FragmentPageAdapter(fragmentManager);
        for (BaseFragmentController<T> fragment : fragments){
            this.fragmentPageAdapter.add(fragment);
        }
        this.viewPager.setAdapter(this.fragmentPageAdapter);
    }

    public static <T extends BaseFragmentController> FragmentManager prepareViewPager(ViewPager viewPager, FragmentActivity fragmentActivity, T... fragments) {
        if (fragments.length == 0) {
            Utils.err("FragmentManager: fragments.length is 0");
            return null;
        }
        if (fragmentPager2FragmentManager.keySet().contains(viewPager)){
            Utils.err("FragmentManager: ViewPager with id " + viewPager.getId() + " is already prepared!");
            return null;
        }
        return fragmentPager2FragmentManager.put(viewPager, new FragmentManager(viewPager, fragmentActivity.getSupportFragmentManager(), fragments));
    }

    public static <T extends BaseFragmentView> FragmentManager get(ViewPager viewPager){
        return fragmentPager2FragmentManager.get(viewPager);
    }

//    private static final Logger LOGGER = Logger.getLogger(FragmentManager.class);
//    private static final LinkedHashMap<Integer, Fragment> key2fragment = new LinkedHashMap<Integer, Fragment>();
//    private static ViewPager viewPager = null;
//    private static FragmentPageAdapter pagerAdapter = null;
//
//    public static void onCreate(Bundle savedInstanceState, ViewPager viewPager, android.support.v4.app.FragmentManager supportFragmentManager) {
//        pagerAdapter = new FragmentPageAdapter(supportFragmentManager);
//        (FragmentManager.viewPager = viewPager).setAdapter(pagerAdapter);
//    }
//
//    public static void addFragment(int key, Fragment fragment) {
//        key2fragment.put(key, fragment);
//        pagerAdapter.add(fragment);
////        FragmentManager.viewPager.setAdapter(pagerAdapter);
//    }
//
//    public static void removeFragment(int key) {
//        pagerAdapter.remove(key2fragment.get(key));
//        key2fragment.remove(key);
////        FragmentManager.viewPager.setAdapter(pagerAdapter);
//    }
//
//    public static Fragment getCurrentFragment() {
//        return pagerAdapter.getItem(viewPager.getCurrentItem());
//    }
//
//    public static void setCurrentFragment(int key) {
//        viewPager.setCurrentItem(pagerAdapter.getItemPosition(key2fragment.get(key)), true);
//    }


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
