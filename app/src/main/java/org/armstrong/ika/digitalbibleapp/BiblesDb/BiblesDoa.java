package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.database.Cursor;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface BiblesDoa {

    @Query("SELECT * FROM t_bible WHERE b=:book AND c=:chap")
    LiveData<List<BiblesEntities>> getBookAndChapter(int book, int chap);

    @Query("SELECT MAX(v) FROM t_bible WHERE b=:book AND c=:chap")
    int getVerseCount(int book, int chap);

    @Query("SELECT MAX(c) FROM t_bible WHERE b=:book")
    int getChapterCount(int book);

    @Query("SELECT * FROM t_bible WHERE t LIKE '%' || :query || '%' AND b BETWEEN :start AND :end ORDER BY b ASC")
    List<BiblesEntities> bibleSearch(String query, int start, int end);

    @Query("SELECT * FROM t_bible WHERE b=:book AND c=:chap AND v=:verse")
    List<BiblesEntities> getCompareValues(int book, int chap, int verse);

    @Query("SELECT t FROM t_bible WHERE b=:book AND c=:chap AND v=:verse")
    Cursor getDataProviderValues(int book, int chap, int verse);


}