package com.github.liosha2007.android.library.database;

import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

/**
 * @author Aleksey Permyakov (23.11.2014).
 */
public interface IDaoCallback {
    int getDatabaseVersion();

    <T extends BaseDaoImpl> String getDatabaseName();

    String getDatabaseDirectory();

    <T extends BaseDaoImpl> T getDao(ConnectionSource connectionSource, Class clazz) throws Exception;

    void onCreate(SQLiteDatabase database, ConnectionSource connectionSource);

    void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion);
}
