package org.armstrong.ika.digitalbibleapp.BookmarkDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static org.armstrong.ika.digitalbibleapp.Constants.BOOKMARKS_DATABASE_NAME;

@Database(entities = {BookmarkEntities.class}, version = 2, exportSchema = false)
public abstract class BookmarkDatabase extends RoomDatabase {

    public abstract BookmarkDoa bookmarkDoa();

    private static BookmarkDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // column types were changed from text to integer
            database.execSQL("BEGIN TRANSACTION;");
            database.execSQL("ALTER TABLE bookmarks RENAME TO bookmarks_old;");
            database.execSQL("CREATE TABLE bookmarks(" +
                    "'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'date' TEXT, " +
                    "'language' TEXT, " +
                    "'abbreviation' TEXT," +
                    "'version' INTEGER NOT NULL," +
                    "'book' INTEGER NOT NULL," +
                    "'book_name' TEXT," +
                    "'chapter' INTEGER NOT NULL," +
                    "'verse' INTEGER NOT NULL," +
                    "'text' TEXT);");

            database.execSQL("INSERT INTO bookmarks(date,language,abbreviation,version,book,book_name,chapter,verse,text)" +
                    "SELECT date,language,abbreviation,version,book,book_name,chapter,verse,text FROM bookmarks_old;");

            database.execSQL("DROP TABLE bookmarks_old;");

            database.execSQL("COMMIT;");
        }
    };

    public static BookmarkDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    BookmarkDatabase.class, BOOKMARKS_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }

        return INSTANCE;
    }


}
