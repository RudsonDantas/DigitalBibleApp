package org.armstrong.ika.digitalbibleapp.VerKeyDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.VERSION_KEY_DATABASE_NAME;

@Database(entities = {VersionEntities.class}, version = 2)
public abstract class VersionDatabase extends RoomDatabase {

    public abstract VersionDoa versionDoa();

    private static VersionDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static VersionDatabase getInstance(final Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    VersionDatabase.class, VERSION_KEY_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }

        return INSTANCE;
    }


}
