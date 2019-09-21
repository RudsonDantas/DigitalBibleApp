package org.armstrong.ika.digitalbibleapp.HightlightDb;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface HighlightDoa {

    @Query("SELECT * FROM highlights WHERE version=:v AND book=:b AND chapter=:c")
    List<HighlightEntities> getChapHighlights(int v, int b, int c);

    @Query("SELECT id FROM highlights WHERE version=:z AND book=:b AND chapter=:c AND verse=:v")
    int getHighlightExists(int z, int b, int c, int v);

    @Query("SELECT * FROM highlights ORDER BY id DESC")
    LiveData<List<HighlightEntities>> getAllHighlights();

//    @Query("SELECT count(*) FROM highlights")
//    int countHighlights();

    @Query("DELETE FROM highlights WHERE id=:id")
    int deleteHighlight(int id); // returns number of deleted rows

    @Query("UPDATE highlights SET color=:c WHERE id=:id")
    int updateColor(int c, int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE) // -1 returned on failure
    long insertHighlight(HighlightEntities highlightEntities);
}
