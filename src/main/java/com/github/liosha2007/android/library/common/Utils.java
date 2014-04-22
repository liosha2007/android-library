package com.github.liosha2007.android.library.common;

import android.app.Activity;
import android.view.View;
import org.apache.log4j.Logger;

/**
 * @author Aleksey Permyakov
 */
public class Utils {
    private static int _uniqueId = 0;
    private static final Logger LOGGER = Logger.getLogger(Utils.class);

    public static <T> T or(T source, T target) {
        return source == null ? target : source;
    }

    public static <T extends View> T view(View view, int id) {
        if (view == null) {
            LOGGER.error("view is null");
            return null;
        }
        return (T) view.findViewById(id);
    }

    public static <T extends View> T view(Activity activity, int id) {
        if (activity == null) {
            LOGGER.error("activity is null");
            return null;
        }
        return (T) activity.findViewById(id);
    }

    public static int makeID() {
        return _uniqueId++;
    }
}
