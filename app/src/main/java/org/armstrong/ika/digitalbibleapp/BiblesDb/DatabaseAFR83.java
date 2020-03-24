package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.AFR83_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseAFR83 extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseAFR83 INSTANCE;

    public static DatabaseAFR83 getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseAFR83.class, AFR83_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_afr83.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
