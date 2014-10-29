package com.github.liosha2007.android.library.common;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    public static void closeStreams(Object... streams) {
        for (Object obj : streams) {
            try {
                if (obj instanceof InputStream) {
                    ((InputStream) obj).close();
                } else if (obj instanceof OutputStream) {
                    ((OutputStream) obj).close();
                }
            } catch (Exception e) {
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

    public static void war(String error_message) {
        Log.w(TAG, isNullOrBlank(error_message) ? "Unknown warning!" : error_message);
    }

    public static void deb(String error_message) {
        Log.d(TAG, error_message);
    }

    public static Drawable loadImageFromAssets(Context context, String assetsPath) {
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

    public static Bitmap stream2bitmap(InputStream inputStream) {
        try {
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            Utils.closeStreams(inputStream);
        }
    }

    public static Bitmap bytes2bitmap(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            return BitmapFactory.decodeStream(byteArrayInputStream);
        } finally {
            Utils.closeStreams(byteArrayInputStream);
        }
    }

    /**
     * Return list with values which will be provided by select object for each element from values
     *
     * @param values list to check
     * @param select object to return value
     * @param <T>    source type
     * @param <R>    result type
     * @return list with values
     */
    public static <T, R> List<R> select(List<T> values, ISelect<T, R> select) {
        List<R> result = new ArrayList<R>(values.size());
        for (T value : values) {
            result.add(select.toAdd(value));
        }
        return result;
    }

    /**
     * Interface for check values
     *
     * @param <T> values type
     */
    public interface ISelect<T, R> {
        R toAdd(T source);
    }


    /**
     * Discard few letters from end of string
     *
     * @param inputString  input string
     * @param discardCount count of letters to discard
     * @return result string
     */
    public static String discard(String inputString, int discardCount) {
        return discard(inputString, discardCount, false);
    }

    /**
     * Discard few letters from string
     *
     * @param inputString  input string
     * @param discardCount count of letters to discard
     * @param fromStart    discard from start of string if true
     * @return result string
     */
    public static String discard(String inputString, int discardCount, boolean fromStart) {
        return discard(inputString, discardCount, fromStart, null);
    }

    /**
     * Discard letters according to regex from end of string
     *
     * @param inputString input string
     * @param matchRegEx  regex for fing letters to discard
     * @return result string
     */
    public static String discard(String inputString, String matchRegEx) {
        return discard(inputString, 0, false, matchRegEx);
    }

    private static String discard(String inputString, int discardCount, boolean fromStart, String matchRegEx) {
        if (inputString == null || discardCount > inputString.length()) {
            return inputString;
        }
        if (matchRegEx == null || matchRegEx.isEmpty()) {
            return fromStart ? inputString.substring(discardCount) : inputString.substring(0, inputString.length() - discardCount);
        }
        return inputString.replaceFirst(matchRegEx, "");
    }
}
