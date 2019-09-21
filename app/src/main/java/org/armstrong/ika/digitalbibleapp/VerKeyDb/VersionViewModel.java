package org.armstrong.ika.digitalbibleapp.VerKeyDb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VersionViewModel extends AndroidViewModel {

    private VersionRepository versionRepository;

    private LiveData<List<VersionEntities>> allActiveVersions, allVersions;

    public VersionViewModel(Application application) {
        super(application);
        versionRepository = new VersionRepository(application);
    }

    public LiveData<List<VersionEntities>> getActiveVersions(int number) {
        allActiveVersions = versionRepository.getActiveVersions(number);
        return allActiveVersions;
    }

//    public LiveData<List<VersionEntities>> getAllVersions() {
//        allVersions = versionRepository.getAllVersions();
//        return allVersions;
//    }

}
