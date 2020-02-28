package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.KJV_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseKJV extends RoomDatabase {

    private static volatile Context context;

    public abstract BiblesDoa biblesDoa();

    private static volatile DatabaseKJV INSTANCE;

    public synchronized static DatabaseKJV getInstance(Context c) {
        context = c;

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseKJV.class, KJV_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_kjv.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
