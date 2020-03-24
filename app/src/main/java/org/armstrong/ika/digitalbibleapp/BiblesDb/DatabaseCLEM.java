package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.CLEM_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseCLEM extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseCLEM INSTANCE;

    public static DatabaseCLEM getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseCLEM.class, CLEM_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_clem.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
