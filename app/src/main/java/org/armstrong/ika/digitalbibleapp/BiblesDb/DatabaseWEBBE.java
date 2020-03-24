package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.WEBBE_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseWEBBE extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseWEBBE INSTANCE;

    public static DatabaseWEBBE getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseWEBBE.class, WEBBE_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_webbe.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
