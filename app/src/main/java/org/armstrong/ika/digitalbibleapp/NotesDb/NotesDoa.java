package org.armstrong.ika.digitalbibleapp.NotesDb;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotesDoa {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    LiveData<List<NotesEntities>> getAllNotes();

    @Query("SELECT * FROM notes")
    List<NotesEntities> getAllUpgradeNotes();

    @Query("SELECT * FROM notes WHERE version=:version AND book=:book AND chapter=:chapter")
    List<NotesEntities> getNoteReferences(int version, int book, int chapter);

    @Query("SELECT * FROM notes WHERE version=:version AND book=:book AND chapter=:chapter AND verse=:verse")
    List<NotesEntities> getNoteAllRefs(int version, int book, int chapter, int verse);

    @Query("UPDATE notes SET date=:date, ref=:ref, text=:txt WHERE id=:id")
    int updateNote(String date, String ref, String txt, int id);

    @Query("DELETE FROM notes WHERE id=:id")
    int deleteNote(int id);

    @Insert
    long insertNote(NotesEntities notesEntities);
}
