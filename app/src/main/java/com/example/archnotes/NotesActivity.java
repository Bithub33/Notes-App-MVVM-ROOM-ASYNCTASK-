package com.example.archnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class NotesActivity extends AppCompatActivity {

    private EditText editText,editText1;
    private int priority,id;
    private int pri = 0;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        pref = getSharedPreferences("comm.example.archnotes",
                Context.MODE_PRIVATE);

        editText = findViewById(R.id.title);
        editText1 = findViewById(R.id.des);

        Intent intent = getIntent();

        if (intent.hasExtra("id"))
        {
            String title = getIntent().getStringExtra("title");
            String des = getIntent().getStringExtra("des");
            pri = getIntent().getIntExtra("pri",1);
            id = getIntent().getIntExtra("id", 1);

            editText.setText(title);
            editText1.setText(des);

        }


        priority = pref.getInt("pr",3);

        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_24);
        setTitle("New Note");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_note) {

            if (pri == 0)
            {
                priority++;
            }
            else
            {
                priority = pri;
            }


            SharedPreferences.Editor edit = pref.edit().putInt("pr",priority);
            edit.commit();
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void saveNote()
    {
        String title = editText.getText().toString();
        String des = editText1.getText().toString();

        if (title.isEmpty() || des.isEmpty())
        {
            Toast.makeText(this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("des", des);
            intent.putExtra("prio", priority);
            intent.putExtra("id", id);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

}