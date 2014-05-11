package com.github.liosha2007.android.library.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

/**
 * Created by liosha on 29.04.14.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {
    private LinkedList<Fragment> fragments = new LinkedList<Fragment>();

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(Fragment fragment){
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    public void remove(Fragment fragment){
        fragments.remove(fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return fragments.indexOf(object);
    }
}
