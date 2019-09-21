package org.armstrong.ika.digitalbibleapp.HightlightDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.HIGHLIGHTS_DATABASE_NAME;

@Database(entities = {HighlightEntities.class}, version = 1, exportSchema = false)
public abstract class HighlightDatabase extends RoomDatabase {

    public abstract HighlightDoa highlightDoa();

    private static HighlightDatabase INSTANCE;

    public static HighlightDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (HighlightDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HighlightDatabase.class, HIGHLIGHTS_DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
