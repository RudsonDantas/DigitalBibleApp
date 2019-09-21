package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.KJV_DATABASE_NAME;

@Database(entities = {BiblesEntities.class}, version = 2)
public abstract class DatabaseKJV extends RoomDatabase {

    private static volatile Context context;

    public abstract BiblesDoa biblesDoa();

    private static volatile DatabaseKJV INSTANCE;


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public synchronized static DatabaseKJV getInstance(Context c) {
        context = c;

        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }

        return INSTANCE;
    }

    private static DatabaseKJV buildDatabase(final Context context) {

        return Room.databaseBuilder(context.getApplicationContext(),
                DatabaseKJV.class, KJV_DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .addCallback(RoomDatabaseCallback)
                .build();

    }

    private static RoomDatabase.Callback RoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Called when the database is created for the first time.
            // This is called after all the tables are created.
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //Called when the database has been opened.
            //Log.e("logg", "onOpen: " + KJV_DATABASE_NAME);
        }
    };


}
