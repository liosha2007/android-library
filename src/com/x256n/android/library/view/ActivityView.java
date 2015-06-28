package com.x256n.android.library.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.x256n.android.library.R;
import com.x256n.android.library.common.Utils;
import com.x256n.android.library.controller.ActivityController;
import com.x256n.android.library.fragment.controller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author liosha (22.02.2015)
 */
public abstract class ActivityView<C extends ActivityController> {
    private final Integer menuLayoutId;
    private final int layoutId;
    private final boolean configureForFragments;
    protected C controller;

    public ActivityView(int layoutId) {
        this(layoutId, null, false);
    }

    public ActivityView(int layoutId, Integer menuLayoutId) {
        this(layoutId, menuLayoutId, false);
    }

    public ActivityView(int layoutId, Integer menuLayoutId, boolean configureForFragments) {
        this.layoutId = layoutId;
        this.menuLayoutId = menuLayoutId;
        this.configureForFragments = configureForFragments;
    }

    public void setController(C controller) {
        this.controller = controller;
    }

    public C getController() {
        return controller;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public Integer getMenuLayoutId() {
        return menuLayoutId;
    }

    public void onCreate() {
        ButterKnife.inject(this, controller);
        if (!configureForFragments) {
            controller.setContentView(layoutId);
        }
        if (menuLayoutId != null) {
            controller.setBehindContentView(menuLayoutId);
            SlidingMenu slidingMenu = controller.getSlidingMenu();
            customizeSlidingMenu(slidingMenu);
        } else {
            controller.setBehindContentView(R.layout.slidingmenumain);
        }

        if (configureForFragments) {
            ViewPager viewPager = new ViewPager(controller);
            configureFragments(viewPager);
        }
        if (menuLayoutId != null) {
            controller.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            controller.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    /**
     * Override this method for configure fragments
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
     * @param fragments
     * @return list of fragments
     */
    protected void createFragments(List<FragmentController> fragments) {
    }

    /**
     * Override for customize menu
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

    public <T extends View> T view(int viewId) {
        T view = Utils.view(controller, viewId);
        if (view == null) {
            view = Utils.view(controller.getSlidingMenu().getRootView(), viewId);
        }
        return view;
    }

    public <T extends View> boolean view(int resourceId, Class<T> clazz, Utils.IViewSuccess<T> successCallback, Utils.IViewFail failCallback) {
        if (Utils.view(controller, resourceId) == null) {
            return Utils.view(controller.getSlidingMenu().getRootView(), resourceId, clazz, successCallback, failCallback);
        } else {
            return Utils.view(controller, resourceId, clazz, successCallback, failCallback);
        }
    }

    public <T extends View> boolean view(int resourceId, Class<T> clazz, Utils.IViewSuccess<T> successCallback) {
        if (Utils.view(controller, resourceId) == null) {
            return Utils.view(controller.getSlidingMenu().getRootView(), resourceId, clazz, successCallback);
        } else {
            return Utils.view(controller, resourceId, clazz, successCallback);
        }
    }

    public <T extends View> boolean view(int resourceId, Utils.IViewSuccess<T> successCallback) {
        if (Utils.view(controller, resourceId) == null) {
            return Utils.view(controller.getSlidingMenu().getRootView(), resourceId, null, successCallback);
        } else {
            return Utils.view(controller, resourceId, null, successCallback);
        }
    }

    /**
     * Override for customize layout
     * @return layout id
     */
    public int getRootLayout() {
        return 0; // Will be used default layout
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T inflate(int viewId) {
        return (T) controller.getLayoutInflater().inflate(viewId, null);
    }
}
