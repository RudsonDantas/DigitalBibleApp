package org.armstrong.ika.digitalbibleapp.LangKeyDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.LANGUAGE_KEY_DATABASE_NAME;

@Database(version = 1, entities = {LangEntities.class})
public abstract class LangDatabase extends RoomDatabase {

    private static volatile Context context;

    public abstract LangDoa langDoa();

    private static LangDatabase INSTANCE;

    public synchronized static LangDatabase getInstance(Context c) {
        context = c;

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LangDatabase.class, LANGUAGE_KEY_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_language_key.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
