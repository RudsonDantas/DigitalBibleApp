package org.armstrong.ika.digitalbibleapp.SearchesDb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface SearchesDoa {

    @Query("DELETE from search_history WHERE date < :offset")
    void deleteSearchesByDate(int offset);

    @Query("SELECT * FROM search_history WHERE version = :version ORDER BY id DESC")
    List<SearchesEntities> getSearchHistory(int version);

    @Query("SELECT * FROM search_history WHERE text=:query AND version=:version")
    boolean entryExits(String query, int version);

    @Insert
    void saveSearchHistory(SearchesEntities searchesEntities);
}
