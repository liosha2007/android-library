package com.github.liosha2007.android.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.github.liosha2007.android.library.common.Utils;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Aleksey Permyakov (23.11.2014).
 */
public class DaoFactory extends OrmLiteSqliteOpenHelper {
    private static IDaoCallback daoCallback;
    private static DaoFactory daoFactory;

    public DaoFactory(Context context) {
        super(context, daoCallback.getDatabaseName(), null, daoCallback.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        daoCallback.onCreate(database, connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        daoCallback.onUpgrade(database, connectionSource, oldVersion, newVersion);
    }

    public static void initialize(IDaoCallback daoCallback) {
        DaoFactory.daoCallback = daoCallback;
    }

    public static <T extends BaseDaoImpl> T daoFor(Context context, Class clazz) {
        if (DaoFactory.daoFactory == null) {
            DaoFactory.daoFactory = OpenHelperManager.getHelper(context, DaoFactory.class);
        }
        try {
            return daoCallback.getDao(daoFactory.getConnectionSource(), clazz);
        } catch (Exception e) {
            Utils.err("Get DAO: " + e.getMessage());
            return null;
        }
    }

    public static boolean exportDatabaseToFile(String path) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(daoCallback.getDatabaseDirectory() + daoCallback.getDatabaseName());
            fileOutputStream = new FileOutputStream(path + File.separator + daoCallback.getDatabaseName());
            IOUtils.copy(fileInputStream, fileOutputStream);
            return true;
        } catch (Exception e) {
            Utils.err("Can't export database: " + e.getMessage());
        } finally {
            Utils.closeStreams(fileInputStream, fileOutputStream);
        }
        return false;
    }

    public static boolean importDatabaseFileFrom(String path) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(daoCallback.getDatabaseDirectory() + daoCallback.getDatabaseName());
            IOUtils.copy(fileInputStream, fileOutputStream);
            return true;
        } catch (Exception e) {
            Utils.err("Can't import database: " + e.getMessage());
        } finally {
            Utils.closeStreams(fileInputStream, fileOutputStream);
        }
        return false;
    }
}
