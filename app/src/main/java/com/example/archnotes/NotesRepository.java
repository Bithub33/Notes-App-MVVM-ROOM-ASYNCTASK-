package com.example.archnotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {

    private NotesDao notesDao;
    private LiveData<List<Notes>> allNotes;

    public NotesRepository(Application application)
    {
        NotesDatabase database = NotesDatabase.getInstance(application);
        notesDao = database.notesDao();
        allNotes = notesDao.getAllNotes();
    }

    public void Insert(Notes notes) {

        new InsertNotesAsyncTask(notesDao).execute(notes);
    }

    public void Update(Notes notes) {

        new UpdateNotesAsyncTask(notesDao).execute(notes);

    }
    public void Delete(Notes notes) {

        new DeleteNotesAsyncTask(notesDao).execute(notes);

    }
    public void DeleteAllNotes() {
        new DeleteAllNotesAsyncTask(notesDao).execute();

    }

    public LiveData<List<Notes>> getAllNotes()
    {
        return allNotes;
    }

    private static class InsertNotesAsyncTask extends AsyncTask<Notes, Void, Void>
    {
        private NotesDao notesDao;

        private InsertNotesAsyncTask(NotesDao notesDao) {

            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.Insert(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private NotesDao notesDao;

        private DeleteAllNotesAsyncTask(NotesDao notesDao) {

            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.deleteAllNotes();
            return null;
        }
    }

    private static class UpdateNotesAsyncTask extends AsyncTask<Notes, Void, Void>
    {
        private NotesDao notesDao;

        private UpdateNotesAsyncTask(NotesDao notesDao) {

            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.Update(notes[0]);
            return null;
        }
    }

    private static class DeleteNotesAsyncTask extends AsyncTask<Notes, Void, Void>
    {
        private NotesDao notesDao;

        private DeleteNotesAsyncTask(NotesDao notesDao) {

            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.Delete(notes[0]);
            return null;
        }
    }

}
