package org.armstrong.ika.digitalbibleapp.CompletedDb;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CompletedDoa {

    @Query("SELECT * FROM completed ORDER BY id DESC")
    LiveData<List<CompletedEntities>> getAllCompletedByTime();

    @Query("SELECT * FROM completed ORDER BY abbreviation DESC")
    LiveData<List<CompletedEntities>> getAllCompletedByAbr();

    @Insert(onConflict = OnConflictStrategy.IGNORE) // -1 returned on failure
    long insertCompleted(CompletedEntities completedEntities);

    @Query("DELETE FROM completed")
    int deleteCompletedData();

    @Query("DELETE FROM completed WHERE id=:id")
    int deleteCompletedById(int id);
}
