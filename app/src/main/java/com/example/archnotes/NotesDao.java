package com.example.archnotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void Insert(Notes notes);

    @Update
    void Update(Notes notes);

    @Delete
    void Delete(Notes notes);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Notes>> getAllNotes();

}
