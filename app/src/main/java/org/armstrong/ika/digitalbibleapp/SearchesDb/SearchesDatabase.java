package org.armstrong.ika.digitalbibleapp.SearchesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.SEARCH_HISTORY_DATABASE_NAME;

@Database(entities = {SearchesEntities.class}, version = 1)
public abstract class SearchesDatabase extends RoomDatabase {

    public abstract SearchesDoa searchesDoa();

    private static SearchesDatabase INSTANCE;

    public static SearchesDatabase getInstance(final Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    SearchesDatabase.class, SEARCH_HISTORY_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }


}
