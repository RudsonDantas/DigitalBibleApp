package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.DN1933_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseDN1933 extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseDN1933 INSTANCE;

    public static DatabaseDN1933 getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseDN1933.class, DN1933_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_dn1933.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
