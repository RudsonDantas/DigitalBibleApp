package org.armstrong.ika.digitalbibleapp.CompletedDb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CompletedViewModel extends AndroidViewModel {

    protected CompletedRepository completedRepository;
    protected LiveData<List<CompletedEntities>> allCompletedByTime;

    public CompletedViewModel(Application application) {
        super(application);
        completedRepository = new CompletedRepository(application);
    }

    public LiveData<List<CompletedEntities>> getAllCompletedByTime() {
        return allCompletedByTime = completedRepository.getAllCompletedByTime();
    }

}
