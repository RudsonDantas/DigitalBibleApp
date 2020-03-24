package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.CPDV_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseCPDV extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseCPDV INSTANCE;

    public static DatabaseCPDV getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseCPDV.class, CPDV_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_cpdv.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
