package org.armstrong.ika.digitalbibleapp.BookmarkDb;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookmarkViewModel extends AndroidViewModel {

    protected BookmarkRepository bookmarkRepository;

    private LiveData<List<BookmarkEntities>> allBookmarks;

    public BookmarkViewModel(Application application) {
        super(application);
        bookmarkRepository = new BookmarkRepository(application);

    }

    public LiveData<List<BookmarkEntities>> getAllBookmarks() {
        allBookmarks = bookmarkRepository.getAllBookmarks();
        return allBookmarks;
    }
}
