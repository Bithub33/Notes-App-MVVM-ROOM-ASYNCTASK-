package com.example.archnotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotesViewModel viewModel;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new NotesAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.floaty);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Notes");


        viewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        viewModel.getAllData().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT
        | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.Delete(adapter.getNotePos(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NotesAdapter.onItemClickListener() {
            @Override
            public void onItemClicked(Notes notes) {

                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                intent.putExtra("title", notes.getTitle());
                intent.putExtra("des", notes.getDescription());
                intent.putExtra("pri", notes.getPriority());
                intent.putExtra("id", notes.getId());
                startActivityForResult(intent,2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode== RESULT_OK)
        {
            assert data != null;
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("des");
            int priority = data.getIntExtra("prio",1);

            Notes notes = new Notes(title,description,priority);
            viewModel.Insert(notes);
        }

        if (requestCode == 2 && resultCode== RESULT_OK)
        {
            assert data != null;
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("des");
            int priority = data.getIntExtra("prio",1);
            int id = data.getIntExtra("id", 1);

            Notes notes = new Notes(title,description,priority);
            notes.setId(id);
            viewModel.Update(notes);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.del_all_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.del_all) {
            deleteAllNotes();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void deleteAllNotes()
    {
        viewModel.DeleteAll();
        Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
    }

}