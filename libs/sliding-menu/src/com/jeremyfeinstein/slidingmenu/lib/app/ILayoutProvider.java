package com.jeremyfeinstein.slidingmenu.lib.app;

/**
 * @author liosha (28.02.2015)
 */
public interface ILayoutProvider {
    /**
     * Override for customize layout
     * @return layout id
     */
    public int getRootLayout();

    /**
     * Get menu layout
     * @return menu layout
     */
    public int getMenuId();
}
