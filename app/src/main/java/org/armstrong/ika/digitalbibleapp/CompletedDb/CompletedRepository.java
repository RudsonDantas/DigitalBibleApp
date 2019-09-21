package org.armstrong.ika.digitalbibleapp.CompletedDb;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CompletedRepository {

    protected CompletedDoa completedDoa;
    protected LiveData<List<CompletedEntities>> allCompletedByTime;

    public CompletedRepository(Context context) {
        completedDoa = CompletedDatabase.getInstance(context).completedDoa();
    }

    public LiveData<List<CompletedEntities>> getAllCompletedByTime() {
        return allCompletedByTime = completedDoa.getAllCompletedByTime();
    }

    public long insertCompleted(CompletedEntities completedEntities){ // -1 returned on failure
       return completedDoa.insertCompleted(completedEntities);
    }

    public int deleteCompletedById(int id) {
        return completedDoa.deleteCompletedById(id);
    }
}
