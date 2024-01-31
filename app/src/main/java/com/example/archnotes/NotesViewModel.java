package com.example.archnotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepository repository;
    private LiveData<List<Notes>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void Insert(Notes notes)
    {
        repository.Insert(notes);
    }

    public void Update(Notes notes)
    {
        repository.Update(notes);
    }

    public void Delete(Notes notes)
    {
        repository.Delete(notes);
    }

    public void DeleteAll()
    {
        repository.DeleteAllNotes();
    }

    public LiveData<List<Notes>> getAllData()
    {
        return allNotes;
    }
}
