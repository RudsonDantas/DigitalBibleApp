package org.armstrong.ika.digitalbibleapp.BookmarkDb;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookmarkRepository {

    protected BookmarkDoa bookmarkDoa;
    private LiveData<List<BookmarkEntities>> allBookmarks;

    public BookmarkRepository(Context context) {
        bookmarkDoa = BookmarkDatabase.getInstance(context).bookmarkDoa();
    }

    public int countBookMarks() {
        return bookmarkDoa.countBookmarks();
    }

    public LiveData<List<BookmarkEntities>> getAllBookmarks() {
        allBookmarks = bookmarkDoa.getAllBookmarks();
        return allBookmarks;
    }

    public int deleteBookmark(int id) {
      return bookmarkDoa.deleteBookmark(id);

    }

    public Long insertBookmark(BookmarkEntities bookmarkEntities) {
        return bookmarkDoa.insertBookmark(bookmarkEntities);
    }


}
