package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.AFR53_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseAFR53 extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseAFR53 INSTANCE;

    public static DatabaseAFR53 getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseAFR53.class, AFR53_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_afr53.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
