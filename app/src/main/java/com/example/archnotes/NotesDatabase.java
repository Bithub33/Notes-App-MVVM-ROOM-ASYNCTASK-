package com.example.archnotes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities ={Notes.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;
    public abstract NotesDao notesDao();

    public static synchronized NotesDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), NotesDatabase.class,
                    "note_table").fallbackToDestructiveMigration().addCallback(RoomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback RoomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NotesDao notesDao;

        private PopulateAsyncTask(NotesDatabase db)
        {
            this.notesDao = db.notesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.Insert(new Notes("title1", "description1", 1));
            notesDao.Insert(new Notes("title2", "description2", 2));
            notesDao.Insert(new Notes("title3", "description3", 3));

            return null;
        }
    }

}
