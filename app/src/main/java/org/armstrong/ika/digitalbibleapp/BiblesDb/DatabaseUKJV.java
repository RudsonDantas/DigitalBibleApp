package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.UKJV_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseUKJV extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseUKJV INSTANCE;

    public static DatabaseUKJV getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseUKJV.class, UKJV_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_ukjv.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
