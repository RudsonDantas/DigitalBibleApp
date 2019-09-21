package org.armstrong.ika.digitalbibleapp.LangKeyDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.LANGUAGE_KEY_DATABASE_NAME;

@Database(entities = {LangEntities.class}, version = 2)
public abstract class LangDatabase extends RoomDatabase {

    private static volatile Context context;

    public abstract LangDoa langDoa();

    private static LangDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public synchronized static LangDatabase getInstance(Context c) {
        context = c;

        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }

        return INSTANCE;
    }

    private static LangDatabase buildDatabase(final Context context) {

        return Room.databaseBuilder(context.getApplicationContext(),
                LangDatabase.class, LANGUAGE_KEY_DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build();

    }


}
