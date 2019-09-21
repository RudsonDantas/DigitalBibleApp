package org.armstrong.ika.digitalbibleapp.NotesDb;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {

    protected NotesDoa notesDoa;
    private LiveData<List<NotesEntities>> allNotes;
    private List<NotesEntities> allNotesReferences, getNoteAllRefs, allUpgradeNotes;

    public NotesRepository(Context context) {
        notesDoa = NotesDatabase.getInstance(context).notesDoa();
    }

    public LiveData<List<NotesEntities>> getAllNotes() {
        allNotes = notesDoa.getAllNotes();
        return allNotes;
    }

    public List<NotesEntities> getAllUpgradeNotes() {
        allUpgradeNotes = notesDoa.getAllUpgradeNotes();
        return allUpgradeNotes;
    }

    public List<NotesEntities> getNoteReferences(int version, int book, int chapter) {
        allNotesReferences = notesDoa.getNoteReferences(version, book, chapter);
        return allNotesReferences;
    }

    public List<NotesEntities> getNoteAllRefs(int version, int book, int chapter, int verse) {
        getNoteAllRefs = notesDoa.getNoteAllRefs(version,book,chapter,verse);
        return getNoteAllRefs;
    }

    public int updateNote(String date, String ref, String txt, int id) {
        return notesDoa.updateNote(date, ref, txt, id);
    }

    public long insertNote(NotesEntities notesEntities) {
        return notesDoa.insertNote(notesEntities);
    }

    public int deleteNote(int id) {
        return notesDoa.deleteNote(id);
    }

}
