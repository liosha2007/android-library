package com.github.liosha2007.android.library.common;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Aleksey Permyakov
 */
public class Utils {
    public static final String TAG = "android-library";
    private static int _uniqueId = 0;
    public static final Gson gson;
    static {
        gson = new Gson();
    }

    public static <T> T or(T source, T target) {
        return source == null ? target : source;
    }

    public static <T extends View> T view(View view, int id) {
        if (view == null) {
            Log.e(TAG, "view is null");
            return null;
        }
        return (T) view.findViewById(id);
    }

    public static <T extends View> T view(Activity activity, int id) {
        if (activity == null) {
            Log.e(TAG, "activity is null");
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
                if (obj instanceof InputStream) {
                    ((InputStream) obj).close();
                } else if (obj instanceof OutputStream) {
                    ((OutputStream) obj).close();
                }
            } catch (Exception e){
                // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * @param param
     * @return
     */
    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }

    public static void err(String error_message) {
        Log.e(TAG, isNullOrBlank(error_message) ? "Unknown error!" : error_message);
    }

    public static void deb(String error_message) {
        Log.d(TAG, error_message);
    }

    public static Drawable loadImageFromAssets(Context context, String assetsPath){
        InputStream inputStream = null;
        try {
            // get input stream
            inputStream = context.getAssets().open(assetsPath);
            // load image as Drawable
            return Drawable.createFromStream(inputStream, null);
        } catch (Exception e) {
            Utils.deb("error loading image from assets: " + e.getMessage());
        } finally {
            Utils.closeStreams(inputStream);
        }
        return null;
    }

    public static void copyText(Context context, String label, String textToCopy) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, textToCopy);
        clipboard.setPrimaryClip(clip);
    }
}
