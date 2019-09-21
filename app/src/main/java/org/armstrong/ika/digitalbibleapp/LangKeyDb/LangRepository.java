package org.armstrong.ika.digitalbibleapp.LangKeyDb;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LangRepository {

    protected LangDoa langDoa;
    private String bookName;
    private LiveData<List<LangEntities>> allBookSearches, allBookReferences;

    public LangRepository(Context context) {
        langDoa = LangDatabase.getInstance(context).langDoa();
    }

    public LiveData<List<LangEntities>> getBookSearch(String name, String code) {
        allBookSearches = langDoa.getBookSearch(name, code);
        return allBookSearches;
    }

    public String getBookName(final int number, final String code) {
        bookName = langDoa.getBookNameFromNumber(number,code);
        return bookName;
    }

    public LiveData<List<LangEntities>> getBookReferences(String code) {
        allBookReferences = langDoa.getBookReferences(code);
        return allBookReferences;
    }


}
