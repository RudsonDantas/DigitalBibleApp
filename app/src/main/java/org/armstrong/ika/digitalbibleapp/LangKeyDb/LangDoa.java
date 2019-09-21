package org.armstrong.ika.digitalbibleapp.LangKeyDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LangDoa {

    @Query("SELECT * FROM language_key WHERE name LIKE :name || '%' AND code=:code ORDER BY number ASC")
    LiveData<List<LangEntities>> getBookSearch(String name, String code);

    @Query("SELECT name FROM language_key WHERE number=:number AND code=:code")
    String getBookNameFromNumber(int number, String code);

    @Query("SELECT * FROM language_key WHERE code=:code ORDER BY number ASC")
    LiveData<List<LangEntities>> getBookReferences(String code);

}