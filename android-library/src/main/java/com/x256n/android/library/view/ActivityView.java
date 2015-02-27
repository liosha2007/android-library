package com.x256n.android.library.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.x256n.android.library.R;
import com.x256n.android.library.controller.ActivityController;
import com.x256n.android.library.fragment.controller.FragmentController;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liosha (22.02.2015)
 */
public abstract class ActivityView<C extends ActivityController> extends BaseActivityView<C> {
    private final int menuLayoutId;
    private final int layoutId;
    private final boolean configureForFragments;
    protected C controller;
    protected View view;

    public ActivityView(int layoutId, int menuLayoutId) {
        this(layoutId, menuLayoutId, false);
    }

    public ActivityView(int layoutId, int menuLayoutId, boolean configureForFragments) {
        this.layoutId = layoutId;
        this.menuLayoutId = menuLayoutId;
        this.configureForFragments = configureForFragments;
    }

    public void setController(C controller) {
        this.controller = controller;
    }

    @Override
    public C getController() {
        return controller;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getMenuLayoutId() {
        return menuLayoutId;
    }

    public void onCreate() {
        if (!configureForFragments) {
            controller.setContentView(layoutId);
        }
        controller.setBehindContentView(menuLayoutId);
        setView(controller.findViewById(android.R.id.content));
        SlidingMenu slidingMenu = controller.getSlidingMenu();
        customizeSlidingMenu(slidingMenu);

        if (configureForFragments) {
            ViewPager viewPager = new ViewPager(controller);
            configureFragments(viewPager);
        }

        controller.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    /**
     * Override this method for configure fragments
     *
     * @param viewPager view pager
     */
    protected void configureFragments(ViewPager viewPager) {
        viewPager.setId(R.id.view_pager_id);
        viewPager.setAdapter(new FragmentPagerAdapter(controller.getSupportFragmentManager()) {
            private List<FragmentController> fragments = new ArrayList<FragmentController>();
            {
                createFragments(fragments);
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        controller.setContentView(viewPager);

        viewPager.setOnPageChangeListener(getFragmentPageChangeListener());
        viewPager.setCurrentItem(0);
    }

    protected ViewPager.OnPageChangeListener getFragmentPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        controller.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    default:
                        controller.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                        break;
                }
            }
        };
    }

    /**
     * Override this method for defile fragment list
     *
     * @return list of fragments
     * @param fragments
     */
    protected void createFragments(List<FragmentController> fragments) {
    }

    /**
     * Override for customize menu
     *
     * @param slidingMenu sliding menu
     */
    protected void customizeSlidingMenu(SlidingMenu slidingMenu) {
        // customize the SlidingMenu
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    @NotNull
    @Override
    protected View getView() {
        return view;
    }

    @Override
    protected void setView(@NotNull View view) {
        this.view = view;
    }
}
