package com.github.liosha2007.android.library.common;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Aleksey Permyakov
 */
public class Utils {
    private static final String TAG = "android-library";
    private static int _uniqueId = 0;
    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    public static final Gson gson;
    static {
        gson = new Gson();
    }

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

    public static void closeStreams(Object ...streams){
        for (Object obj : streams) {
            try {
                if (obj == null){
                    continue;
                } else if (obj instanceof InputStream) {
                    ((InputStream) obj).close();
                } else if (obj instanceof OutputStream) {
                    ((OutputStream) obj).close();
                } else {
                    // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, "Object '" + obj + "' can't be closed!");
                }
            } catch (Exception e){
                // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return str == null ? true : false;
    }

    /**
     * @param param
     * @return
     */
    public static boolean isNullOrBlank(String param) {
        if (isNull(param) || param.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static void err(String error_message) {
        Log.e(TAG, isNullOrBlank(error_message) ? "Unknown error!" : error_message);
    }

    public static void deb(String error_message) {
        Log.d(TAG, error_message);
    }
}
