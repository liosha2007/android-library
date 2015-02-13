package com.x256n.android.library.common

import android.view.View
import android.util.Log
import android.content.Context
import android.app.Activity
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Method
import android.graphics.drawable.Drawable
import android.content.ClipboardManager
import android.content.ClipData
import android.graphics.Bitmap
import java.io.ByteArrayInputStream
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.os.Build
import java.io.File

/**
 * @author Aleksey Permyakov (13.02.2015)
 */
public object Utils {
    val LOG_TAG = "x256n-android-library"
    private var _uniqueId: Int = 0

    /**
     * Return source in it's not null or target
     */
    public fun <T: Any> or(source: T?, target: T?): T? = source ?: target

    public fun <T: View> view(view: View, resourceId: Int): T? = view.findViewById(resourceId) as? T

    public fun <T: View> view(activity: Activity, resourceId: Int): T? = activity.findViewById(resourceId) as? T

    public fun err(message: String): Int = Log.e(LOG_TAG, "Application error: ${message}")

    public fun deb(message: String): Int = Log.d(LOG_TAG, "Application error: ${message}")

    public fun war(message: String): Int = Log.w(LOG_TAG, "Application error: ${message}")

    public fun makeID(): Int = _uniqueId++

    public fun isNullOrEmpty(str: String?): Boolean = str?.isEmpty() ?: true

    public fun makeAndroidVersion(): String = "Android SDK: ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})"

    public fun makeTempFilePath(context: Context): File = File.createTempFile("temp_", ".tmp", context.getCacheDir())

    public fun closeStreams(vararg streams: Any?): Unit =
        streams.forEach { stream ->
            try {
                if (stream is InputStream) {
                    stream.close()
                } else if (stream is OutputStream) {
                    stream.close()
                } else if (stream != null) {
                    var close = stream.javaClass.getDeclaredMethod("close")
                    close?.invoke(stream)
                }
            } catch (e: Exception){ }
        }

    public fun loadImageFromAssets(context: Context, assetsPath: String): Drawable {
        var inputStream: InputStream? = null;
        try {
            inputStream = context.getAssets().open(assetsPath)
            return Drawable.createFromStream(inputStream, null);
        } finally {
            closeStreams(inputStream);
        }
    }

    public fun copyText(context: Context, label: String, textToCopy: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, textToCopy))
    }

    public fun bytes2bitmap(bytes: ByteArray): Bitmap {
        val inputStream = ByteArrayInputStream(bytes)
        try {
            return BitmapFactory.decodeStream(inputStream)
        } finally {
            closeStreams(inputStream)
        }
    }

    public fun haveInternet(context: Context): Boolean {
        val networkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).getActiveNetworkInfo()
        return networkInfo == null || !networkInfo.isConnected() || !networkInfo.isRoaming()
    }

    public fun makeDisplayInfo(context: Context): String {
        val density = context.getResources().getDisplayMetrics().densityDpi
        val screenLayout: Int = context.getResources().getConfiguration().screenLayout
        val screenSize: Int = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        val stringBuilder = StringBuilder()
        when (density){
            DisplayMetrics.DENSITY_LOW -> stringBuilder.append("ldpi-")
            DisplayMetrics.DENSITY_MEDIUM -> stringBuilder.append("mdpi-")
            DisplayMetrics.DENSITY_HIGH -> stringBuilder.append("hdpi-")
            DisplayMetrics.DENSITY_XHIGH -> stringBuilder.append("xhdpi-")
            DisplayMetrics.DENSITY_XXHIGH -> stringBuilder.append("xxhdpi-")
        }
        when (screenSize) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> stringBuilder.append("large")
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> stringBuilder.append("normal")
            Configuration.SCREENLAYOUT_SIZE_SMALL -> stringBuilder.append("small")
            Configuration.SCREENLAYOUT_SIZE_XLARGE -> stringBuilder.append("xlarge")
            else -> stringBuilder.append("unknown")
        }
        return stringBuilder.toString()
    }
}