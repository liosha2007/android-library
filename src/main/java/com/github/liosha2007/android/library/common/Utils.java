package com.github.liosha2007.android.library.common;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.net.InetAddress;
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

    public static boolean haveInternet(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to disable internet while roaming, just return false
            return true;
        }
        return true;
    }
    
    public static String makeDisplayInfo(Context context){
        int density = context.getResources().getDisplayMetrics().densityDpi;
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
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
                break;
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

    public static String makeAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return "Android SDK: " + sdkVersion + " (" + release +")";
    }


    public static interface ICallback{
        public void onUploaded(String imageUrl);
    }

    public static void uploadImage(final byte[] imageBytes, final String imageName, final String apiKey, final String apiSecret, final ICallback callback){
        new AsyncTask<byte[], Void, String>(){
            @Override
            protected String doInBackground(byte[]... params) {
                try {
                    FTPClient ftpClient = new FTPClient();
                    ftpClient.connect(InetAddress.getByName("wen.ru"));
                    ftpClient.login(apiKey, apiSecret);
                    ftpClient.changeWorkingDirectory("images");
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.enterLocalPassiveMode();
                    FTPFile[] ftpFiles = ftpClient.listFiles();
                    for (FTPFile ftpFile : ftpFiles) {
                        if (imageName.equals(ftpFile.getName())) {
                            return "http://sw-manual.wen.ru/images/" + imageName;
                        }
                    }
                    ftpClient.storeFile(imageName, new ByteArrayInputStream(params[0]));
                    ftpClient.logout();
                    ftpClient.disconnect();
                    return "http://sw-manual.wen.ru/images/" + imageName;
                } catch (Exception e){
                    Utils.err("Facebook image upload error: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String url) {
                callback.onUploaded(url);
            }
        }.execute(imageBytes);
    }

    public static File makeTempFilePath(Context context) throws IOException {
        File outputDir = context.getCacheDir(); // context being the Activity pointer
        return File.createTempFile("temp_", ".tmp", outputDir);
    }
}
