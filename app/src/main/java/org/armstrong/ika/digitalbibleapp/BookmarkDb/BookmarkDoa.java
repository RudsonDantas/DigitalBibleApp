package org.armstrong.ika.digitalbibleapp.BookmarkDb;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BookmarkDoa {

    @Query("SELECT * FROM bookmarks ORDER BY id DESC")
    LiveData<List<BookmarkEntities>> getAllBookmarks();

    @Query("SELECT count(*) FROM bookmarks")
    int countBookmarks();

    @Query("DELETE FROM bookmarks WHERE id=:id")
    int deleteBookmark(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE) // -1 returned on failure
    long insertBookmark(BookmarkEntities bookmarkModel);
}
