package org.armstrong.ika.digitalbibleapp.Notes;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import org.armstrong.ika.digitalbibleapp.NotesDb.NotesEntities;
import org.armstrong.ika.digitalbibleapp.NotesDb.NotesRepository;

import java.util.List;

public class NotesUpgrade { // is this class still necessary?

    Context context;
    protected NotesRepository notesRepository;

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor sharedPreferencesEditor;

    public NotesUpgrade(Context context) {
        this.context = context;

        // read saved preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public void doNotesUpgrade() {

        if(isUpgraded() == "no") {

            notesRepository = new NotesRepository(context);

            List<NotesEntities> notesEntities = notesRepository.getAllUpgradeNotes();

            for (int i = 0; i < notesEntities.size(); i++) {
                //Log.e("logg", "doNotesUpgrade: " + notesEntities.get(i).getRef());
                String[] parts = notesEntities.get(i).getRef().split(" ");
                //Log.e("logg", "doNotesUpgrade: " + parts[0] );
            }

        }

    }

    private String isUpgraded() {
        return sharedPreferences.getString("notesUpgraded", "no");
    }

    private void markAsUpgraded() {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("notesUpgraded", "yes");
        sharedPreferencesEditor.apply();
    }
}
