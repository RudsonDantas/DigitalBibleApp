package org.armstrong.ika.digitalbibleapp;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import org.armstrong.ika.digitalbibleapp.Common.CopyFromAssests;

import static org.armstrong.ika.digitalbibleapp.Constants.VERSION_KEY_DATABASE_NAME;

public class GeneralCopier {

    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    // database version
    private int DB_VERSION;

    // database name
    private String DB_NAME;

    // DB copier
    CopyFromAssests copier;

    public GeneralCopier(Context context, String dbName) {
        this.context = context;
        this.DB_NAME = dbName;

        // read saved preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public void initializeCopier(int dbVersion) {
        this.DB_VERSION = dbVersion;

        if (installedDatabaseIsOutdated()) {

            copier = new CopyFromAssests(context,DB_NAME);

            if (copier.status) {
                writeDatabaseVersionInPreferences();
            }

        }

    }

    private boolean installedDatabaseIsOutdated() {

        int version = sharedPreferences.getInt(DB_NAME, 0);
        return (version < DB_VERSION) ? true : false;

    }

    private void writeDatabaseVersionInPreferences() {

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt(DB_NAME, DB_VERSION);

        if(DB_NAME == VERSION_KEY_DATABASE_NAME) {
            sharedPreferencesEditor.putInt("version", 1); // reset version to KJV
        }

        sharedPreferencesEditor.apply();
    }


}



