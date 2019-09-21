package org.armstrong.ika.digitalbibleapp.CompletedDb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.COMPLETED_DATABASE_NAME;

@Database(entities = {CompletedEntities.class}, version = 1, exportSchema = false)
public abstract class CompletedDatabase extends RoomDatabase {

    public abstract CompletedDoa completedDoa();

    public static CompletedDatabase INSTANCE;

    public static CompletedDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CompletedDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CompletedDatabase.class, COMPLETED_DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();

                }
            }

        }

        return INSTANCE;
    }
}
