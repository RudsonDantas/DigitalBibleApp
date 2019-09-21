package org.armstrong.ika.digitalbibleapp.NotesDb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    NotesRepository notesRepository;

    private LiveData<List<NotesEntities>> allNotes;

    public NotesViewModel(Application application) {
        super(application);

        notesRepository = new NotesRepository(application);

    }

    public LiveData<List<NotesEntities>> getAllNotes() {
        allNotes = notesRepository.getAllNotes();
        return allNotes;
    }
}
