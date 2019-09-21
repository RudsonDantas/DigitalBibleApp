package org.armstrong.ika.digitalbibleapp.NotesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.NOTES_DATABASE_NAME;

@Database(entities = {NotesEntities.class}, version = 2, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDoa notesDoa();

    private static NotesDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // add new fields
            database.execSQL("BEGIN TRANSACTION;");

            database.execSQL("ALTER TABLE notes RENAME TO notes_old;");

            database.execSQL("CREATE TABLE notes(" +
                    "'id' INTEGER PRIMARY KEY NOT NULL, " +
                    "'ref' TEXT, " +
                    "'date' TEXT, " +
                    "'text' TEXT, " +
                    "'version' INTEGER NOT NULL DEFAULT 0," +
                    "'book' INTEGER NOT NULL DEFAULT 0," +
                    "'chapter' INTEGER NOT NULL DEFAULT 0," +
                    "'verse' INTEGER NOT NULL DEFAULT 0);");

            database.execSQL("INSERT INTO notes(ref,date,text)" +
                    " SELECT ref,date,text FROM notes_old;");

            database.execSQL("DROP TABLE notes_old;");

            database.execSQL("COMMIT;");
        }
    };

    public static NotesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDatabase.class, NOTES_DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
