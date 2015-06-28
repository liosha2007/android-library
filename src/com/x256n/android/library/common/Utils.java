package com.x256n.android.library.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author liosha (15.03.2015)
 */
public final class Utils {
    public static String LOG_TAG = "x256n-android-library";
    private static int _uniqueId = 0;

    /**
     * Return source in it's not null or target
     */
    public static <T> T or(T source, T target) {
        return source == null ? target : source;
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public static <T extends View> T view(View view, int resourceId) {
        return (T) view.findViewById(resourceId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> boolean view(View view, int resourceId, Class<T> clazz, IViewSuccess<T> successCallback) {
        return view(view, resourceId, clazz, successCallback, new IViewFail() {
            @Override
            public void fail(Integer resourceId, String message) {
                Utils.err("Resource ID: " + resourceId + " Message: " + message);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> boolean view(View view, int resourceId, Class<T> clazz, IViewSuccess<T> successCallback, IViewFail failCallback) {
        if (view != null) {
            final View foundView = view.findViewById(resourceId);
            if (foundView == null && failCallback != null) {
                failCallback.fail(resourceId, "View not found!");
            } else if (foundView != null) {
                if ((clazz != null && clazz.isInstance(foundView)) || (clazz == null && View.class.isInstance(foundView))) {
                    successCallback.success((T) foundView);
                } else if (failCallback != null) {
                    failCallback.fail(resourceId, "Incompatibility class type: " + foundView.getClass().getName());
                }
            }
        } else if (failCallback != null) {
            failCallback.fail(resourceId, "Parent view is null");
        }
        return false;
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public static <T extends View> T view(Activity activity, int resourceId) {
        return (T) activity.findViewById(resourceId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> boolean view(Activity activity, int resourceId, Class<T> clazz, IViewSuccess<T> successCallback) {
        return view(activity, resourceId, clazz, successCallback, new IViewFail() {
            @Override
            public void fail(Integer resourceId, String message) {
                Utils.err("Resource ID: " + resourceId + " Message: " + message);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> boolean view(Activity activity, int resourceId, Class<T> clazz, IViewSuccess<T> successCallback, IViewFail failCallback) {
        if (activity != null) {
            final View foundView = activity.findViewById(resourceId);
            if (foundView == null && failCallback != null) {
                failCallback.fail(resourceId, "View not found!");
            } else if (foundView != null) {
                if ((clazz != null && clazz.isInstance(foundView)) || (clazz == null && View.class.isInstance(foundView))) {
                    successCallback.success((T) foundView);
                } else if (failCallback != null) {
                    failCallback.fail(resourceId, "Incompatibility class type: " + foundView.getClass().getName());
                }
            }
        } else if (failCallback != null) {
            failCallback.fail(resourceId, "Parent activity is null");
        }
        return false;
    }

    public static interface IViewSuccess<T extends View> {
        void success(T view);
    }

    public static interface IViewFail {
        void fail(Integer resourceId, String message);
    }

    public static int err(String message) {
        return Log.e(LOG_TAG, "Application error: " + message);
    }

    public static int deb(String message) {
        return Log.d(LOG_TAG, "Application error: " + message);
    }

    public static int war(String message) {
        return Log.w(LOG_TAG, "Application error: " + message);
    }

    public static int makeID() {
        return _uniqueId++;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String makeAndroidVersion() {
        return "Android SDK: ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})";
    }

    public static String makeTempFilePath(Context context) throws Exception {
        return context == null ? null : File.createTempFile("temp_", ".tmp", context.getCacheDir()).getAbsolutePath();
    }

    public static Drawable loadImageFromAssets(Context context, String assetsPath) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(assetsPath);
            return Drawable.createFromStream(inputStream, null);
        } finally {
            closeStreams(inputStream);
        }
    }

    public static void copyText(Context context, String label, String textToCopy) {
        if (context == null) {
            return;
        }
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(textToCopy);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(label, textToCopy);
            clipboard.setPrimaryClip(clip);
        }
    }

    public static Bitmap bytes2bitmap(byte[] bytes) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            closeStreams(inputStream);
        }
    }

    public static boolean haveInternet(Context context) throws Exception {
        if (context == null) {
            throw new Exception("context is null");
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String makeDisplayInfo(Context context) throws Exception {
        if (context == null) {
            throw new Exception("context is null");
        }
        int density = context.getResources().getDisplayMetrics().densityDpi;
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        int screenSize = screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        StringBuilder stringBuilder = new StringBuilder();
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                stringBuilder.append("ldpi-");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                stringBuilder.append("mdpi-");
                break;
            case DisplayMetrics.DENSITY_HIGH:
                stringBuilder.append("hdpi-");
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                stringBuilder.append("xhdpi-");
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                stringBuilder.append("xxhdpi-");
        }
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                stringBuilder.append("large");
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                stringBuilder.append("normal");
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                stringBuilder.append("small");
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                stringBuilder.append("xlarge");
                break;
            default:
                stringBuilder.append("unknown");
        }
        return stringBuilder.toString();
    }

    public static void closeStreams(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                try {
                    final Method declaredMethod = o.getClass().getDeclaredMethod("close");
                    if (declaredMethod != null) {
                        declaredMethod.invoke(o);
                    }
                } catch (Exception e) {
                    Utils.war(e.getMessage());
                }
            }
        }
    }
}
