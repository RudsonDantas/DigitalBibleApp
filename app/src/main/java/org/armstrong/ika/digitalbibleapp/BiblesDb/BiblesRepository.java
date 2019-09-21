package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BiblesRepository {

    Context context;

    protected BiblesDoa biblesDoa;
    public static String lang;

    protected LiveData<List<BiblesEntities>> bookAndChapter;
    protected List<BiblesEntities> allCompareValues, bibleSearch;

    public BiblesRepository(Context context) {
        this.context = context;
    }

    public BiblesRepository initialize(int version) {
        biblesDoa = getDoa(version);
        return null;
    }

    public int getVerseCount(int book, int chap) {
        return biblesDoa.getVerseCount(book, chap);
    }

    public List<BiblesEntities> getCompareValues(int book, int chap, int verse){
        return allCompareValues = biblesDoa.getCompareValues(book,chap,verse);
    }

    public List<BiblesEntities> bibleSearch(String query, int start, int end){
        return bibleSearch = biblesDoa.bibleSearch(query, start, end);
    }

    public int getChapterCount(int book) {
        return biblesDoa.getChapterCount(book);
    }

    public LiveData<List<BiblesEntities>> getBookAndChapter(int book, int chap){
        bookAndChapter = biblesDoa.getBookAndChapter(book,chap);
        return bookAndChapter;
    }

    public Cursor getDataProviderValues(int book, int chap, int verse){
        return biblesDoa.getDataProviderValues(book,chap,verse);
    }

    private BiblesDoa getDoa(int version) {

        BiblesDoa Doa = null;

        switch (version) {
            case 1:
                Doa = DatabaseKJV.getInstance(context).biblesDoa();
                lang = "eng";
                break;
            case 2:
                Doa = DatabaseCLEM.getInstance(context).biblesDoa();
                lang = "lat";
                break;
            case 3:
                Doa = DatabaseCPDV.getInstance(context).biblesDoa();
                lang = "eng";
                break;
            case 4:
                Doa = DatabaseNOVA.getInstance(context).biblesDoa();
                lang = "lat";
                break;
            case 5:
                Doa = DatabaseAFR53.getInstance(context).biblesDoa();
                lang = "afr";
                break;
            case 6:
                Doa = DatabaseDN1933.getInstance(context).biblesDoa();
                lang = "dan";
                break;
            case 7:
                Doa = DatabaseUKJV.getInstance(context).biblesDoa();
                lang = "eng";
                break;
            case 8:
                Doa = DatabaseWEBBE.getInstance(context).biblesDoa();
                lang = "eng";
                break;
            case 9:
                Doa = DatabaseAFR83.getInstance(context).biblesDoa();
                lang = "afr";
                break;
        }

        return Doa;

    }

}


