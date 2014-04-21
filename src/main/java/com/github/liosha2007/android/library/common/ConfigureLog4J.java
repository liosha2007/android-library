package com.github.liosha2007.android.library.common;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Level;


import java.io.File;

import static com.github.liosha2007.android.library.common.Utils.or;

/**
 * @author Aleksey Permyakov
 */
public class ConfigureLog4J {
    /**
     * Configure logger
     * NOTE: In order to log to a file on the external storage, the following permission needs to be placed in AndroidManifest.xml.
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     * @param logFilePath
     * @param rootLevel
     * @param loggerName
     * @param level
     */
    public static void configure(String logFilePath, Level rootLevel, String loggerName, Level level) {
        final LogConfigurator logConfigurator = new LogConfigurator();

        logConfigurator.setFileName(or(logFilePath, Environment.getExternalStorageDirectory() + File.separator + "application.log"));
        logConfigurator.setRootLevel(or(rootLevel, Level.DEBUG));
        // Set log level of a specific logger
        logConfigurator.setLevel(or(loggerName, "org.apache"), or(level, Level.ERROR));
        logConfigurator.configure();
    }
}
