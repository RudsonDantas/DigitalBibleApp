package org.armstrong.ika.digitalbibleapp.HightlightDb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HighlightViewModel extends AndroidViewModel {

    protected HighlightRepository highlightRepository;
    protected LiveData<List<HighlightEntities>> allHighlights;

    public HighlightViewModel(Application application) {
        super(application);
        highlightRepository = new HighlightRepository(application);

    }

    public LiveData<List<HighlightEntities>> getAllHighlights(){
       return allHighlights = highlightRepository.getAllHighlights();
    }
}
