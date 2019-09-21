package org.armstrong.ika.digitalbibleapp.LangKeyDb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LanguageKeyViewModel extends AndroidViewModel {

    private LangRepository langRepository;

    private LiveData<List<LangEntities>> allBookReferences, allBookSearches;

    public LanguageKeyViewModel(Application application) {
        super(application);
        langRepository = new LangRepository(application);
    }

    public LiveData<List<LangEntities>> getAllReferences(String code){
        allBookReferences = langRepository.getBookReferences(code);
        return allBookReferences;
    }

    public LiveData<List<LangEntities>> getBookSearch(String name, String code){
        allBookSearches = langRepository.getBookSearch(name, code);
        return allBookSearches;
    }

}
