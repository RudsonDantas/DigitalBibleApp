package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.CPDV_DATABASE_NAME;

@Database(entities = {BiblesEntities.class}, version = 2)
public abstract class DatabaseCPDV extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseCPDV INSTANCE;


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static DatabaseCPDV getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseCPDV.class, CPDV_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }

        return INSTANCE;
    }


}
